package com.danske.bank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@NoArgsConstructor
@Table(name="Account")
@Getter
@Setter
@SuperBuilder
@Audited
public class Account extends CommonEntity {
    @OneToMany(mappedBy = "account")
    private List<Customer> customers;
    private int numberOfOwners;
}
