package com.tecacet.payments;

import com.tecacet.payments.entity.*;
import com.tecacet.payments.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class TestData {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PayeeRepository payeeRepository;

    @Autowired
    private PayeeProfileRepository payeeProfileRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private AccountRepository accountRepository;

    private final Random random = new Random();

    public void createTestData(int numCustomers, int numPayees) {

        var customers = IntStream.range(0, numCustomers)
                .mapToObj(i -> createCustomer("name" + i))
                .collect(Collectors.toList());
        customerRepository.saveAll(customers);

        var payees = IntStream.range(0, numPayees)
                .mapToObj(i -> createPayee("name" + i))
                .collect(Collectors.toList());
        payeeRepository.saveAll(payees);

        List<PayeeProfileEntity> profiles = new ArrayList<>();
        List<AccountEntity> accounts = new ArrayList<>();
        for (CustomerEntity customer : customers) {
            PayeeEntity payee = payees.get(random.nextInt(payees.size()));
            AccountEntity account = createAccount(customer);
            accounts.add(account);
            profiles.add(createProfile(customer, payee, account));

        }
        accountRepository.saveAll(accounts);
        payeeProfileRepository.saveAll(profiles);


        LocalDate dueDate = LocalDate.now().plusDays(5);
        var invoices = profiles.stream().map(profile -> createInvoice(profile, dueDate))
                        .collect(Collectors.toList());
        invoiceRepository.saveAll(invoices);
    }

    private CustomerEntity createCustomer(String name) {
        return CustomerEntity.builder().name(name).build();
    }

    private PayeeEntity createPayee(String name) {
        return PayeeEntity.builder().name(name).build();
    }

    private PayeeProfileEntity createProfile(CustomerEntity customer, PayeeEntity payee, AccountEntity account) {
        return PayeeProfileEntity.builder()
                .customer(customer)
                .payee(payee)
                .sourceAccount(account)
                .paymentMethod(PayeeProfileEntity.PaymentMethod.ACH_TRANSFER)
                .daysAhead(10)
                .build();
    }

    private AccountEntity createAccount(CustomerEntity customer) {
        return AccountEntity.builder()
                .customer(customer)
                .maximumWithdrawalLimit(BigDecimal.valueOf(2000))
                .minimumBalanceRequirement(BigDecimal.valueOf(100))
                .build();
    }

    private InvoiceEntity createInvoice(PayeeProfileEntity profile, LocalDate dueDate) {
        return InvoiceEntity.builder()
                .customer(profile.getCustomer())
                .payee(profile.getPayee())
                .dueDate(dueDate)
                .minimumAmount(BigDecimal.valueOf(100))
                .totalAmount(BigDecimal.valueOf(1000))
                .status(InvoiceEntity.Status.DUE)
                .build();
    }
}
