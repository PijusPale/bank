package com.danske.bank.mapper;

import com.danske.bank.dto.*;
import com.danske.bank.entity.Account;
import com.danske.bank.entity.Address;
import com.danske.bank.entity.Customer;

public class CustomerMapper {
    public static CustomerResponseDto toDto(Customer customer) {
        return CustomerResponseDto.builder()
                .id(customer.getId())
                .customerType(customer.getCustomerType())
                .name(customer.getName())
                .lastname(customer.getLastname())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .accounts(customer.getAccounts().stream().map(CustomerMapper::toDto).toList())
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
                .accounts(customer.getAccountIds().stream().map(CustomerMapper::toEntity).toList())
                .build();
    }

    public static AccountResponseDto toDto(Account account) {
        return AccountResponseDto.builder()
                .id(account.getId())
                .numberOfOwners(account.getNumberOfOwners())
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

    public static Account toEntity(Long accountId) {
        return Account.builder().id(accountId).build();
    }

    public static Address toEntity(AddressRequestDto addressDto) {
        return Address.builder().country(addressDto.getCountry())
                .addressLine(addressDto.getAddressLine())
                .zipCode(addressDto.getZipCode())
                .build();
    }

}
