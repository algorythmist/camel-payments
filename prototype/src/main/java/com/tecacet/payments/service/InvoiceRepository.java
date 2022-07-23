package com.tecacet.payments.service;

import com.tecacet.payments.model.Customer;
import com.tecacet.payments.model.Invoice;
import com.tecacet.payments.model.Payee;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository {

    //query unpaid invoices where due date <= :date
    List<Invoice> findInvoices(Customer customer, Payee payee, LocalDate date);

}
