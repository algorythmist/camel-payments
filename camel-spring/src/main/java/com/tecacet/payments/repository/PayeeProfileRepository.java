package com.tecacet.payments.repository;

import com.tecacet.payments.entity.CustomerEntity;
import com.tecacet.payments.entity.PayeeProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PayeeProfileRepository extends JpaRepository<PayeeProfileEntity, UUID> {

    List<PayeeProfileEntity> findByCustomer(CustomerEntity customer);
}
