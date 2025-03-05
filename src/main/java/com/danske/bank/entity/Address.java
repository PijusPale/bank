package com.danske.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.Objects;

@Entity
@Table(name="Address")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Audited
public class Address extends CommonEntity {
    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;
    @NotNull
    private String country;
    private String addressLine;
    private String zipCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCustomer(), address.getCustomer()) && Objects.equals(getCountry(), address.getCountry()) && Objects.equals(getAddressLine(), address.getAddressLine()) && Objects.equals(getZipCode(), address.getZipCode());
    }
}
