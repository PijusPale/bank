package com.danske.bank.service;

import com.danske.bank.CustomerRepository;
import com.danske.bank.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
