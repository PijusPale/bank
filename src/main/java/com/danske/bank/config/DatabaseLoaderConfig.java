package com.danske.bank.config;

import com.danske.bank.entity.Account;
import com.danske.bank.repository.AccountRepository;
import com.danske.bank.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.danske.bank.entity")
public class DatabaseLoaderConfig {
    private static final Logger log = LoggerFactory.getLogger(DatabaseLoaderConfig.class);

    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository) {

        var account = accountRepository.save(Account.builder()
                .numberOfOwners(0)
                .build());

        accountRepository.save(Account.builder()
                .numberOfOwners(0)
                .build());

        return args -> log.info("Preloading " + account);
    }
}
