package com.danske.bank.mapper;

import com.danske.bank.dto.*;
import com.danske.bank.entity.Account;
import com.danske.bank.entity.Address;
import com.danske.bank.entity.Customer;
import jakarta.annotation.Nonnull;

public class CustomerMapper {
    public static CustomerResponseDto toDto(Customer customer) {
        return CustomerResponseDto.builder()
                .id(customer.getId())
                .customerType(customer.getCustomerType())
                .name(customer.getName())
                .lastname(customer.getLastname())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .accountId(customer.getAccount().getId())
                .addresses(customer.getAddresses().stream().map(CustomerMapper::toDto).toList())
                .build();
    }

    public static Customer toEntity(CustomerRequestDto customer) {
        return Customer.builder()
                .name(customer.getName())
                .customerType(customer.getCustomerType())
                .lastname(customer.getLastname())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .addresses(customer.getAddresses().stream().map(CustomerMapper::toEntity).toList())
                .account(Account.builder().id(customer.getAccountId()).build())
                .build();
    }

    public static Customer toEntity(@Nonnull CustomerRequestDto customer,
                                    @Nonnull Long customerId) {
        return Customer.builder()
                .id(customerId)
                .name(customer.getName())
                .customerType(customer.getCustomerType())
                .lastname(customer.getLastname())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .addresses(customer.getAddresses().stream().map(CustomerMapper::toEntity).toList())
                .account(Account.builder().id(customer.getAccountId()).build())
                .build();
    }

    public static AddressResponseDto toDto(Address address) {
        return AddressResponseDto.builder()
                .id(address.getId())
                .country(address.getCountry())
                .addressLine(address.getAddressLine())
                .zipCode(address.getZipCode())
                .build();
    }

    public static Address toEntity(AddressRequestDto addressDto) {
        return Address.builder()
                .id(addressDto.getId())
                .country(addressDto.getCountry())
                .addressLine(addressDto.getAddressLine())
                .zipCode(addressDto.getZipCode())
                .build();
    }

}
