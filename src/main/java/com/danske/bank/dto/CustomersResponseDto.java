package com.danske.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class CustomersResponseDto {
    @JsonProperty("count")
    int count;
    @JsonProperty("customers")
    List<CustomerResponseDto> customers;

}
