package com.danske.bank.entity;

import com.danske.bank.enums.ECustomerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.List;

@Getter
@SuperBuilder
@Entity
@Table(name="Customer")
@Audited
public class Customer extends CommonEntity {
    @Enumerated(EnumType.STRING)
    private ECustomerType customerType;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String email;
    @ManyToMany(mappedBy = "owners")
    private List<Account> accounts;
    @OneToMany(mappedBy = "customerId")
    private List<Address> addresses;
}
