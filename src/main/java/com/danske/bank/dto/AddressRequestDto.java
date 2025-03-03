package com.danske.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddressRequestDto {
    @JsonProperty("country")
    private final String country;
    @JsonProperty("addressLine")
    private final String addressLine;
    @JsonProperty("zipCode")
    private final String zipCode;
}
