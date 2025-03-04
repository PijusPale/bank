package com.danske.bank.service;

import com.danske.bank.entity.Account;
import com.danske.bank.repository.CustomerRepository;
import com.danske.bank.entity.Customer;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountService accountService;
    private final AddressService addressService;

    @Transactional
    public Customer createCustomer(Customer customer) {
        var accountId = customer.getAccount().getId();
        var maybeAccount = accountService.getAccountById(accountId);
        var account = validateAccount(maybeAccount);
        validateCustomerInAccount(customer, account);

        Customer createdCustomer;

        try {
            createdCustomer = customerRepository.save(customer);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Customer with these unique attributes already exists",
                        ex
                );
            }
            throw ex;
        }

        customer.getAddresses().forEach(address ->
                addressService.createAddress(address, customer.getId()));

        accountService.updateAccount(account, 1);

        return createdCustomer;
    }

    @Transactional
    public Customer updateCustomer(@Nonnull Customer customer) {

        Customer existingCustomer = validateCustomer(customerRepository.findById(customer.getId()));

        Customer updatedCustomer;

        if (!Objects.equals(customer.getAccount().getId(), existingCustomer.getAccount().getId())) {

            var maybeNewAccount = accountService.getAccountById(customer.getAccount().getId());
            var newAccount = validateAccount(maybeNewAccount);

            validateCustomerInAccount(customer, newAccount);

            var maybeOldAccount = accountService.getAccountById(existingCustomer.getAccount().getId());
            var oldAccount = validateAccount(maybeOldAccount);
            accountService.updateAccount(newAccount, 1);
            accountService.updateAccount(oldAccount, -1);
        }

        var mergedCustomers = mergeCustomers(existingCustomer, customer);

        try {
            updatedCustomer = customerRepository.save(mergedCustomers);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Customer with these unique attributes already exists",
                        ex
                );
            }
            throw ex;
        }

        var addressIds = addressService.findAllByCustomerId(existingCustomer.getId());
        addressService.deleteAddresses(addressIds);

        customer.getAddresses().forEach(address ->
                addressService.createAddress(address, customer.getId()));

        return updatedCustomer;
    }

    private Customer mergeCustomers(Customer existingCustomer, Customer customer) {
        existingCustomer.setCustomerType(customer.getCustomerType());
        existingCustomer.setName(customer.getName());
        existingCustomer.setLastname(customer.getLastname());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setAccount(customer.getAccount());

        return existingCustomer;
    }

    private Account validateAccount(Optional<Account> maybeAccount) {
        if (maybeAccount.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account not found"
            );
        }

        return maybeAccount.get();
    }

    private Customer validateCustomer(Optional<Customer> maybeCustomer) {
        if (maybeCustomer.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Customer not found"
            );
        }

        return maybeCustomer.get();
    }

    private void validateCustomerInAccount(@Nonnull Customer customer,
                                           @Nonnull Account account) {
        var alreadyExists = account.getCustomers().stream()
                .anyMatch(accOwner -> accOwner.equals(customer));

        if (alreadyExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Customer is already an owner of this account"
            );
        }
    }

    public List<Customer> findAll(String searchTerm, Pageable pageable) {
        return customerRepository.findBySearchTerm(searchTerm, pageable);
    }
}