package com.danske.bank.service;

import com.danske.bank.entity.Account;
import com.danske.bank.repository.AccountRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Transactional
    public Account updateAccount(@Nonnull Account sourceAccount,
                                 int increment) {
        Account existingAccount = accountRepository.findById(sourceAccount.getId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")
                );

        existingAccount.setNumberOfOwners(existingAccount.getNumberOfOwners() + increment);
        return accountRepository.save(existingAccount);
    }
}
