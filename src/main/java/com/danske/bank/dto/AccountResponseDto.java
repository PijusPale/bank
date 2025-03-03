package com.danske.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class AccountResponseDto {
    @JsonProperty("id")
    Long id;
    @JsonProperty("numberOfOwners")
    int numberOfOwners;
}
