package com.tecacet.payments.prototype;

import com.tecacet.payments.model.CustomerIdentifier;
import com.tecacet.payments.model.Invoice;
import com.tecacet.payments.model.PayeeIdentifier;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository {

    //query unpaid invoices where due date <= :date
    List<Invoice> findInvoices(CustomerIdentifier customerIdentifier, PayeeIdentifier payeeIdentifier, LocalDate date);

}
