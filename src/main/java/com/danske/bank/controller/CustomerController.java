package com.danske.bank.controller;

import com.danske.bank.dto.CustomerRequestDto;
import com.danske.bank.dto.CustomerResponseDto;
import com.danske.bank.dto.CustomersResponseDto;
import com.danske.bank.mapper.CustomerMapper;
import com.danske.bank.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    ResponseEntity<CustomerResponseDto> postCustomer(@Valid @RequestBody CustomerRequestDto customerDto) {

        return new ResponseEntity<>(
                CustomerMapper.toDto(customerService.createCustomer(CustomerMapper.toEntity(customerDto))),
                HttpStatusCode.valueOf(CREATED.value()));
    }

    @PutMapping("/customer/{id}")
    ResponseEntity<CustomerResponseDto> updateCustomer(@Valid @RequestBody CustomerRequestDto customerDto,
                                                       @PathVariable Long id) {
        return new ResponseEntity<>(
                CustomerMapper.toDto(customerService.updateCustomer(CustomerMapper.toEntity(customerDto, id))),
                HttpStatusCode.valueOf(CREATED.value()));
    }

    @GetMapping("/customers")
    ResponseEntity<CustomersResponseDto> getAllCustomers(@RequestParam String searchTerm,
                                                         @RequestParam int page,
                                                         @RequestParam int rowNumber) {
        Pageable pageable = PageRequest.of(page, rowNumber);

        var result = customerService.findAll(searchTerm, pageable).stream()
                .map(CustomerMapper::toDto)
                .toList();


        return new ResponseEntity<>(CustomersResponseDto.builder()
                .customers(result)
                .count(result.size())
                .build(), HttpStatusCode.valueOf(OK.value()));
    }
}
