package com.tecacet.payments.service;

import com.tecacet.payments.entity.InvoiceEntity;
import com.tecacet.payments.entity.PayeeProfileEntity;
import com.tecacet.payments.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public List<InvoiceEntity> findInvoices(PayeeProfileEntity payeeProfile) {
        LocalDate date = LocalDate.now(); //TODO: pass the date
        LocalDate dueHorizon = date.plusDays(payeeProfile.getDaysAhead());
        return invoiceRepository.findInvoices(payeeProfile.getCustomer(),
                payeeProfile.getPayee(), dueHorizon, InvoiceEntity.Status.DUE);
    }

}
