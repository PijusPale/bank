package com.danske.bank.dto;

import com.danske.bank.enums.ECustomerType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Builder
@Getter
public class CustomerRequestDto {
    @NonNull
    private Long accountId;
    @NonNull
    private String name;
    @NonNull
    private String lastname;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String email;
    @NonNull
    private ECustomerType customerType;
    private List<AddressRequestDto> addresses;
}
