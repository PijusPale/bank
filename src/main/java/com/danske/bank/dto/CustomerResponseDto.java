package com.danske.bank.dto;

import com.danske.bank.enums.ECustomerType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class CustomerResponseDto {
    @JsonProperty("id")
    Long id;
    @JsonProperty("customerType")
    ECustomerType customerType;
    @JsonProperty("name")
    String name;
    @JsonProperty("lastname")
    String lastname;
    @JsonProperty("phoneNumber")
    String phoneNumber;
    @JsonProperty("email")
    String email;
    @JsonProperty("accountId")
    Long accountId;
    @JsonProperty("addresses")
    List<AddressResponseDto> addresses;

}
