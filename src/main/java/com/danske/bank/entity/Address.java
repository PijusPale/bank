package com.danske.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

@Entity
@Table(name="Address")
@Getter
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
}
