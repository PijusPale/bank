package com.danske.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="Address")
@Getter
@SuperBuilder
public class Address extends CommonEntity {

    private Long customerId;
    private String country;
    private String addressLine;
    private String zipCode;
}
