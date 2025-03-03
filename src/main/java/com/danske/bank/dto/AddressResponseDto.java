package com.danske.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class AddressResponseDto {

    @JsonProperty("id")
    Long id;
    @JsonProperty("country")
    String country;
    @JsonProperty("addressLine")
    String addressLine;
    @JsonProperty("zipCode")
    String zipCode;
}
