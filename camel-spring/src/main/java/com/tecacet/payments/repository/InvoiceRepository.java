package com.tecacet.payments.repository;


import com.tecacet.payments.entity.CustomerEntity;
import com.tecacet.payments.entity.InvoiceEntity;
import com.tecacet.payments.entity.PayeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, UUID> {

    @Query("from InvoiceEntity i where i.customer = :customer and i.payee = :payee and i.dueDate <= :date and i.status = :status")
    List<InvoiceEntity> findInvoices(@Param("customer") CustomerEntity customer,
                                     @Param("payee") PayeeEntity payee,
                                     @Param("date") LocalDate date,
                                     @Param("status") InvoiceEntity.Status status);
}
