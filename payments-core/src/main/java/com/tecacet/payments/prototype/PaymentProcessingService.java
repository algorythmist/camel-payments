package com.tecacet.payments.prototype;

import com.tecacet.payments.model.*;
import com.tecacet.payments.processor.PaymentsProcessor;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class PaymentProcessingService {

    private final PayeeProfileRepository payeeProfileRepository;
    private final InvoiceRepository invoiceRepository;
    private final AccountRepository accountRepository;
    private final PaymentsProcessor paymentsProcessor;

    private final DelegatingTransferService transferService;

    public void processPayments(Customer customer, LocalDate date) {
        List<InvoiceProfile> invoiceProfiles = collectCustomerInvoices(customer, date);
        val invoicesByAccount = invoiceProfiles.stream()
                .collect(groupingBy(InvoiceProfile::getAccountIdentifier,
                        toList()));
        val payments = payInvoices(invoicesByAccount);
        payments.forEach(transferService::executePayment);
    }


    private List<Payment> payInvoices(Map<AccountIdentifier, List<InvoiceProfile>> invoicesByAccount) {
        List<Payment> allPayments = new ArrayList<>();
        for (Map.Entry<AccountIdentifier, List<InvoiceProfile>> entry : invoicesByAccount.entrySet()) {
            AccountIdentifier accountIdentifier = entry.getKey();
            var account = accountRepository.getAccount(accountIdentifier);
            allPayments.addAll(paymentsProcessor.payInvoices(entry.getValue(), account));
        }
        return allPayments;
    }

    private List<InvoiceProfile> collectCustomerInvoices(Customer customer, LocalDate date) {
        List<PayeeProfile> profiles = payeeProfileRepository.findPayeeProfiles(customer.getCustomerIdentifier());
        List<InvoiceProfile> customerInvoices = new ArrayList<>();
        for (PayeeProfile profile : profiles) {
            LocalDate dueHorizon = date.plusDays(profile.getDaysAhead());
            var invoices = invoiceRepository
                    .findInvoices(customer.getCustomerIdentifier(), profile.getPayee(), dueHorizon)
                    .stream().map(invoice -> new InvoiceProfile(invoice, profile)).toList();
            customerInvoices.addAll(invoices);
        }
        return customerInvoices;
    }
}