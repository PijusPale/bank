package com.danske.bank.mapper

import com.danske.bank.dto.AddressRequestDto
import com.danske.bank.dto.CustomerRequestDto
import com.danske.bank.entity.Account
import com.danske.bank.entity.Address
import com.danske.bank.entity.Customer
import com.danske.bank.enums.ECustomerType
import spock.lang.Specification

class CustomerMapperSpec extends Specification {

    def customerMapper = Mock(CustomerMapper)

    def static NAME = "NAME"
    def static CUSTOMER_TYPE = ECustomerType.INDIVIDUAL
    def static LASTNAME = "LASTNAME"
    def static PHONE_NUMBER = "+37069876543"
    def static EMAIL = "test@test.com"
    def static COUNTRY = "LT"
    def static ADDRESS_LINE = "J. Basanavičiaus g. 20, Vilnius"
    def static ZIP_CODE = "LT-12345"
    def static ACCOUNT_ID = 123L
    def static CUSTOMER_ID = 184518L
    def static ADDRESS_ID = 999L

    def addressEntity = Address.builder()
            .id(ADDRESS_ID)
            .country(COUNTRY)
            .addressLine(ADDRESS_LINE)
            .zipCode(ZIP_CODE)
            .build()

    def accountEntity = Account.builder()
            .id(ACCOUNT_ID).build()

    def customerEntity = Customer.builder()
            .id(CUSTOMER_ID)
            .name(NAME)
            .customerType(CUSTOMER_TYPE)
            .lastname(LASTNAME)
            .phoneNumber(PHONE_NUMBER)
            .email(EMAIL)
            .addresses(List.of(addressEntity))
            .account(accountEntity)
            .build()

    def addressRequestDto = AddressRequestDto.builder()
            .country(COUNTRY)
            .addressLine(ADDRESS_LINE)
            .zipCode(ZIP_CODE)
            .build()

    def customerRequestDto = CustomerRequestDto.builder()
            .name(NAME)
            .customerType(CUSTOMER_TYPE)
            .lastname(LASTNAME)
            .phoneNumber(PHONE_NUMBER)
            .email(EMAIL)
            .addresses(List.of(addressRequestDto))
            .accountId(ACCOUNT_ID)
            .build()

    def "should correctly map Address to Dto"() {
        when:
        def resultDto = customerMapper.toDto(addressEntity)

        then:
        with(resultDto) {
            assert id == ADDRESS_ID
            assert country == COUNTRY
            assert addressLine == ADDRESS_LINE
            assert zipCode == ZIP_CODE
        }
    }

    def "should correctly map Customer to Dto"() {
        when:
        def resultDto = customerMapper.toDto(customerEntity)

        then:
        with(resultDto) {
            assert id == CUSTOMER_ID
            assert customerType == CUSTOMER_TYPE
            assert name == NAME
            assert lastname == LASTNAME
            assert phoneNumber == PHONE_NUMBER
            assert email == EMAIL
            assert accountId == ACCOUNT_ID

            addresses.forEach {
                assert it.id == ADDRESS_ID
                assert it.country == COUNTRY
                assert it.addressLine == ADDRESS_LINE
                assert it.zipCode == ZIP_CODE
            }
        }
    }

    def "should correctly map Dto to Customer"() {
        when:
        def resultEntity = customerMapper.toEntity(customerRequestDto)

        then:
        with(resultEntity) {
            assert customerType == CUSTOMER_TYPE
            assert name == NAME
            assert lastname == LASTNAME
            assert phoneNumber == PHONE_NUMBER
            assert email == EMAIL
            assert account.getId() == ACCOUNT_ID

            addresses.forEach {
                assert it.country == COUNTRY
                assert it.addressLine == ADDRESS_LINE
                assert it.zipCode == ZIP_CODE
            }
        }
    }

    def "should correctly map Dto and customerId to Customer"() {
        when:
        def resultEntity = customerMapper.toEntity(customerRequestDto, CUSTOMER_ID)

        then:
        with(resultEntity) {
            assert id == CUSTOMER_ID
            assert customerType == CUSTOMER_TYPE
            assert name == NAME
            assert lastname == LASTNAME
            assert phoneNumber == PHONE_NUMBER
            assert email == EMAIL
            assert account.getId() == ACCOUNT_ID

            addresses.forEach {
                assert it.country == COUNTRY
                assert it.addressLine == ADDRESS_LINE
                assert it.zipCode == ZIP_CODE
            }
        }
    }

    def "should correctly map Dto to Address"() {
        when:
        def resultEntity = customerMapper.toEntity(addressRequestDto)

        then:
        with(resultEntity) {
            assert it.country == COUNTRY
            assert it.addressLine == ADDRESS_LINE
            assert it.zipCode == ZIP_CODE
        }
    }
}
