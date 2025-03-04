package com.danske.bank.entity;

import com.danske.bank.enums.ECustomerType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "Customer",
        uniqueConstraints = { @UniqueConstraint(name = "UniqueCustomerConstraint", columnNames = {"name", "lastname", "phoneNumber", "email"})})
@NoArgsConstructor
@Audited
public class Customer extends CommonEntity {
    @NotNull
    @Enumerated(EnumType.STRING)
    private ECustomerType customerType;
    @NotNull
    private String name;
    @NotNull
    private String lastname;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String email;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getName(), customer.getName()) && Objects.equals(getLastname(), customer.getLastname()) && Objects.equals(getPhoneNumber(), customer.getPhoneNumber()) && Objects.equals(getEmail(), customer.getEmail());
    }
}
