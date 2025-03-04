package com.danske.bank.config;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomAuditAwareListener implements AuditorAware<String> {

    @Override
    public @NonNull Optional<String> getCurrentAuditor() {

        return Optional.of("USER1");
    }
}
