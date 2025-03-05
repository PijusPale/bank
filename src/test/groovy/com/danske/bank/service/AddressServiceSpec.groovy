package com.danske.bank.service

import com.danske.bank.entity.Address
import com.danske.bank.repository.AddressRepository
import spock.lang.Specification

class AddressServiceSpec extends Specification {
    def addressRepository = Mock(AddressRepository)
    def addressService = new AddressService(addressRepository)

    def static CUSTOMER_ID = 123456L
    def static ADDRESS_IDS = List.of(4654321L, 99999L)
    def static COUNTRY = "LT"
    def static ADDRESS_LINE = "J. Basanavičiaus g. 20, Vilnius"
    def static ZIP_CODE = "LT-12345"

    def addressEntity = Address.builder()
            .country(COUNTRY)
            .addressLine(ADDRESS_LINE)
            .zipCode(ZIP_CODE)
            .build()

    def "createAddress should create a new address"() {
        when:
        addressService.createAddress(addressEntity, CUSTOMER_ID)

        then:
        1 * addressRepository.save(_)
        noExceptionThrown()
    }

    def "deleteAddresses should delete all passed addresses"() {
        when:
        addressService.deleteAddresses(ADDRESS_IDS)

        then:
        ADDRESS_IDS.forEach {
            1 * addressRepository.deleteById(it)
        }
        noExceptionThrown()
    }

    def "findAllByCustomerId should successfully find addresses"() {
        when:
        def result = addressService.findAllByCustomerId(CUSTOMER_ID)

        then:
        1 * addressRepository.findAllByCustomerId(CUSTOMER_ID) >> ADDRESS_IDS
        result.forEach {
            ADDRESS_IDS.contains(it)
        }
        noExceptionThrown()
    }
}
