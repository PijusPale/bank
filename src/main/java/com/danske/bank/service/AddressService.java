package com.danske.bank.service;

import com.danske.bank.entity.Address;
import com.danske.bank.entity.Customer;
import com.danske.bank.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    @Transactional
    public void createAddress(Address address,
                              Long customerId) {
        addressRepository.save(Address.builder()
                .customer(Customer.builder().id(customerId).build())
                .country(address.getCountry())
                .zipCode(address.getZipCode())
                .addressLine(address.getAddressLine())
                .build());
    }

    public void deleteAddresses(List<Long> addressIds) {
        addressIds.forEach(addressRepository::deleteById);
    }

    public List<Long> findAllByCustomerId(Long customerId) {
        return addressRepository.findAllByCustomerId(customerId);
    }
}
