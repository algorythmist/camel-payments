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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PayeeEntity extends BaseEntity {
    @EqualsAndHashCode.Include
    private String name;
}
