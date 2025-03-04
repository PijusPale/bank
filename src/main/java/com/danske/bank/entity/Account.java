package com.danske.bank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Account")
@Getter
@SuperBuilder
@Audited
public class Account extends CommonEntity {
    @ManyToMany
    @JoinTable(name = "account_customer",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "customerId")
    )
    private Set<Customer> owners;
    private int numberOfOwners;
}
