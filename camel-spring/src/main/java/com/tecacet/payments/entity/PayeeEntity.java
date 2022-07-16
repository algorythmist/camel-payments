package com.tecacet.payments.entity;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
public class PayeeEntity extends BaseEntity {
    private String name;
}
