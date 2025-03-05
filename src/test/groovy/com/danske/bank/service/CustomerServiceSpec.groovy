package com.danske.bank.service

import com.danske.bank.entity.Account
import com.danske.bank.entity.Address
import com.danske.bank.entity.Customer
import com.danske.bank.enums.ECustomerType
import com.danske.bank.repository.CustomerRepository
import org.springframework.data.domain.Pageable
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification

class CustomerServiceSpec extends Specification {
    def customerRepository = Mock(CustomerRepository)
    def accountService = Mock(AccountService)
    def addressService = Mock(AddressService)
    def customerService = new CustomerService(customerRepository, accountService, addressService)

    def static NAME = "NAME"
    def static CUSTOMER_TYPE = ECustomerType.INDIVIDUAL
    def static LASTNAME = "LASTNAME"
    def static PHONE_NUMBER = "+37069876543"
    def static EMAIL = "test@test.com"
    def static COUNTRY = "LT"
    def static ADDRESS_LINE = "J. Basanavičiaus g. 20, Vilnius"
    def static ZIP_CODE = "LT-12345"
    def static NEW_ACCOUNT_ID = 123L
    def static OLD_ACCOUNT_ID = 987L
    def static NEW_CUSTOMER_ID = 184518L
    def static ADDRESS_ID = 999L

    def addressEntity = Address.builder()
            .id(ADDRESS_ID)
            .country(COUNTRY)
            .addressLine(ADDRESS_LINE)
            .zipCode(ZIP_CODE)
            .build()


    def newCustomerEntity = Customer.builder()
            .id(NEW_CUSTOMER_ID)
            .name(NAME)
            .customerType(CUSTOMER_TYPE)
            .lastname(LASTNAME)
            .phoneNumber(PHONE_NUMBER)
            .email(EMAIL)
            .addresses(List.of(addressEntity))
            .account(Account.builder()
                    .id(NEW_ACCOUNT_ID).build())
            .build()

    def newAccountEntity = Account.builder()
            .id(NEW_ACCOUNT_ID)
            .customers(List.of(Customer.builder().build())).build()

    def oldCustomerEntity = Customer.builder()
            .id(NEW_CUSTOMER_ID)
            .name(NAME + NAME)
            .customerType(CUSTOMER_TYPE)
            .lastname(LASTNAME)
            .phoneNumber(PHONE_NUMBER)
            .email(EMAIL)
            .addresses(List.of(addressEntity))
            .account(Account.builder()
                    .id(OLD_ACCOUNT_ID).build())
            .build()

    def oldAccountEntity = Account.builder()
            .id(OLD_ACCOUNT_ID)
            .customers(List.of(oldCustomerEntity)).build()

    def "createCustomer should successfully create a customer"() {
        when:
        customerService.createCustomer(newCustomerEntity)

        then:
        1 * accountService.getAccountById(NEW_ACCOUNT_ID) >> Optional.of(oldAccountEntity)
        1 * customerRepository.save(newCustomerEntity) >> newCustomerEntity
        newCustomerEntity.getAddresses().forEach {
            1 * addressService.createAddress(it, NEW_CUSTOMER_ID)
        }

        1 * accountService.updateAccount(oldAccountEntity, 1)
        noExceptionThrown()
    }

    def "createCustomer should throw exception when passed accountId does not exist"() {
        when:
        customerService.createCustomer(newCustomerEntity)

        then:
        1 * accountService.getAccountById(NEW_ACCOUNT_ID) >> Optional.empty()
        def ex = thrown(ResponseStatusException)
        assert ex.getReason() == "Account not found"
    }

    def "createCustomer should throw exception when customer is already in passed account"() {
        when:
        customerService.createCustomer(oldCustomerEntity)

        then:
        1 * accountService.getAccountById(OLD_ACCOUNT_ID) >> Optional.of(oldAccountEntity)

        def ex = thrown(ResponseStatusException)
        assert ex.getReason() == "Customer is already an owner of this account"
    }

    def "updateCustomer should successfully update a customer with new account"() {
        when:
        customerService.updateCustomer(newCustomerEntity)

        then:
        1 * customerRepository.findById(NEW_CUSTOMER_ID) >> Optional.of(oldCustomerEntity)
        1 * accountService.getAccountById(NEW_ACCOUNT_ID) >> Optional.of(newAccountEntity)
        1 * accountService.getAccountById(OLD_ACCOUNT_ID) >> Optional.of(oldAccountEntity)
        1 * accountService.updateAccount(newAccountEntity, 1)
        1 * accountService.updateAccount(oldAccountEntity, -1)
        1 * customerRepository.save(oldCustomerEntity) >> oldCustomerEntity
        1 * addressService.findAllByCustomerId(NEW_CUSTOMER_ID) >> List.of(ADDRESS_ID)

        newCustomerEntity.getAddresses().forEach {
            1 * addressService.createAddress(it, NEW_CUSTOMER_ID)
        }

        noExceptionThrown()
    }

    def "updateCustomer should successfully update a customer with old account"() {
        when:
        customerService.updateCustomer(newCustomerEntity)

        then:
        1 * customerRepository.findById(NEW_CUSTOMER_ID) >> Optional.of(newCustomerEntity)
        0 * accountService.getAccountById(NEW_ACCOUNT_ID) >> Optional.of(newAccountEntity)
        0 * accountService.getAccountById(OLD_ACCOUNT_ID) >> Optional.of(oldAccountEntity)
        0 * accountService.updateAccount(newAccountEntity, 1)
        0 * accountService.updateAccount(oldAccountEntity, -1)
        1 * customerRepository.save(newCustomerEntity) >> newCustomerEntity
        1 * addressService.findAllByCustomerId(NEW_CUSTOMER_ID) >> List.of(ADDRESS_ID)

        newCustomerEntity.getAddresses().forEach {
            1 * addressService.createAddress(it, NEW_CUSTOMER_ID)
        }

        noExceptionThrown()
    }

    def "updateCustomer should throw exception when passed customer does not exist"() {
        when:
        customerService.updateCustomer(newCustomerEntity)

        then:
        1 * customerRepository.findById(NEW_CUSTOMER_ID) >> Optional.empty()

        def ex = thrown(ResponseStatusException)
        assert ex.getReason() == "Customer not found"
    }

    def "updateCustomer should throw exception when passed account does not exist"() {
        when:
        customerService.updateCustomer(newCustomerEntity)

        then:
        1 * customerRepository.findById(NEW_CUSTOMER_ID) >> Optional.of(oldCustomerEntity)
        1 * accountService.getAccountById(NEW_ACCOUNT_ID) >> Optional.empty()

        def ex = thrown(ResponseStatusException)
        assert ex.getReason() == "Account not found"
    }

    def "findAll should successfully get customers by search term"() {
        when:
        customerService.findAll(_ as String, _ as Pageable)

        then:
        1 * customerRepository.findBySearchTerm(*_) >> List.of(newCustomerEntity)
        noExceptionThrown()
    }
}
