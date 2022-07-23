package com.tecacet.payments.service;

import com.tecacet.payments.core.PaymentsProcessor;
import com.tecacet.payments.model.*;
import com.tecacet.payments.repository.AccountBalanceRepository;
import com.tecacet.payments.repository.PayeeProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class PaymentProcessingService {

    private final PayeeProfileRepository payeeProfileRepository;
    private final InvoiceRepository invoiceRepository;
    private final AccountBalanceRepository accountBalanceRepository;
    private final PaymentsProcessor paymentsProcessor;

    private final DelegatingTransferService transferService;

    public void processPayments(Customer customer, LocalDate date) {
        List<InvoiceProfile> invoiceProfiles = collectCustomerInvoices(customer, date);
        val invoicesByAccount = invoiceProfiles.stream()
                .collect(groupingBy(InvoiceProfile::getAccount,
                        toList()));
        val payments = payInvoices(invoicesByAccount);
        payments.forEach(transferService::executePayment);
    }


    private List<Payment> payInvoices(Map<Account, List<InvoiceProfile>> invoicesByAccount) {
        List<Payment> allPayments = new ArrayList<>();
        for (Map.Entry<Account, List<InvoiceProfile>> entry : invoicesByAccount.entrySet()) {
            Account account = entry.getKey();
            var balance = accountBalanceRepository.getAccountBalance(account);
            allPayments.addAll(paymentsProcessor.payInvoices(account, entry.getValue(), balance));
        }
        return allPayments;
    }

    private List<InvoiceProfile> collectCustomerInvoices(Customer customer, LocalDate date) {
        List<PayeeProfile> profiles = payeeProfileRepository.findPayeeProfiles(customer);
        List<InvoiceProfile> customerInvoices = new ArrayList<>();
        for (PayeeProfile profile : profiles) {
            LocalDate dueHorizon = date.plusDays(profile.getDaysAhead());
            var invoices = invoiceRepository.findInvoices(customer, profile.getPayee(), dueHorizon)
                    .stream().map(invoice -> new InvoiceProfile(invoice, profile)).toList();
            customerInvoices.addAll(invoices);
        }
        return customerInvoices;
    }
}