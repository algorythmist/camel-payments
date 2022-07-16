package com.tecacet.payments.repository;


import com.tecacet.payments.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    default List<CustomerEntity> findAllCustomers() {
        return findAll();
    }
}
