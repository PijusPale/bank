package com.danske.bank.service;

import com.danske.bank.entity.Address;
import com.danske.bank.entity.Customer;
import com.danske.bank.repository.AddressRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    private void updateAddress(@Nonnull Address sourceAddress) {
        Address existingAddress = addressRepository.findById(sourceAddress.getId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found")
                );

        existingAddress.setAddressLine(sourceAddress.getAddressLine());
        existingAddress.setCountry(sourceAddress.getCountry());
        existingAddress.setZipCode(sourceAddress.getZipCode());
        addressRepository.save(existingAddress);
    }

    public void updateAddresses(List<Address> newAddresses) {
        newAddresses.forEach(this::updateAddress);
    }
}
