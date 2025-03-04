package com.danske.bank.controller;

import com.danske.bank.dto.CustomerRequestDto;
import com.danske.bank.dto.CustomerResponseDto;
import com.danske.bank.mapper.CustomerMapper;
import com.danske.bank.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final CustomerService customerService;

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    ResponseEntity<CustomerResponseDto> postCustomer(@Valid @RequestBody CustomerRequestDto customerDto) {

        return new ResponseEntity<>(
                CustomerMapper.toDto(customerService.createCustomer(CustomerMapper.toEntity(customerDto))),
                HttpStatusCode.valueOf(CREATED.value()));
    }

    @PutMapping("/account/{id}")
    ResponseEntity<CustomerResponseDto> updateMessage(@Valid @RequestBody CustomerRequestDto newCustomerDto,
                                                      @PathVariable Long id) {
        return new ResponseEntity<>(
                CustomerMapper.toDto(customerService.updateCustomer(CustomerMapper.toEntity(newCustomerDto, id))),
                HttpStatusCode.valueOf(CREATED.value()));
    }
}
