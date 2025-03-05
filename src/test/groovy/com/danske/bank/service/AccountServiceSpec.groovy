package com.danske.bank.service

import com.danske.bank.entity.Account
import com.danske.bank.repository.AccountRepository
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification

class AccountServiceSpec extends Specification {
    def accountRepository = Mock(AccountRepository)
    def accountService = new AccountService(accountRepository)

    def ACCOUNT_ID = 123L

    def accountEntity = Account.builder()
            .id(ACCOUNT_ID)
            .numberOfOwners(3).build()


    def "getAccountById should successfully find Account"() {
        when:
        def result = accountService.getAccountById(ACCOUNT_ID)

        then:
        1 * accountRepository.findById(ACCOUNT_ID) >> Optional.of(accountEntity)
        result.isPresent()
        with(result.get()) {
            assert id == ACCOUNT_ID
            assert numberOfOwners == 3
        }
        noExceptionThrown()
    }

    def "updateAccount should successfully update Account"() {
        when:
        def result = accountService.updateAccount(accountEntity, 1)

        then:
        1 * accountRepository.findById(ACCOUNT_ID) >> Optional.of(accountEntity)
        1 * accountRepository.save(_) >> accountEntity

        with(result) {
            assert id == ACCOUNT_ID
            assert numberOfOwners == 4
        }
        noExceptionThrown()
    }

    def "updateAccount should throw exception when there is no such account"() {
        when:
        accountService.updateAccount(accountEntity, 1)

        then:
        1 * accountRepository.findById(ACCOUNT_ID) >> Optional.empty()
        def ex = thrown(ResponseStatusException)
        assert ex.getReason() == "Account not found"
    }
}
