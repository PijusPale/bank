package com.danske.bank.dto;

import com.danske.bank.enums.ECustomerType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CustomerRequestDto {
    private List<Long> accountIds;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String email;
    private ECustomerType customerType;
    private List<AddressRequestDto> addresses;
}
