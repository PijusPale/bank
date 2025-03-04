package com.danske.bank.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class AddressRequestDto {

    @NonNull
    private final String country;
    private final String addressLine;
    private final String zipCode;
}
