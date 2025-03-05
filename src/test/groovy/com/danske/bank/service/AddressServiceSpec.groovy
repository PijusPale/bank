package com.danske.bank.service

import com.danske.bank.entity.Address
import com.danske.bank.repository.AddressRepository
import spock.lang.Specification

import javax.swing.text.html.Option

class AddressServiceSpec extends Specification {
    def addressRepository = Mock(AddressRepository)
    def addressService = new AddressService(addressRepository)

    def static CUSTOMER_ID = 123456L
    def static COUNTRY = "LT"
    def static ADDRESS_LINE = "J. Basanavičiaus g. 20, Vilnius"
    def static ZIP_CODE = "LT-12345"

    def addressEntity = Address.builder()
            .id(99999L)
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

    def "updateAddresses should successfully update Addresses"() {
        when:
        addressService.updateAddresses(List.of(addressEntity))

        then:
        1 * addressRepository.findById(99999L) >> Optional.of(addressEntity)
        1 * addressRepository.save(addressEntity)
        noExceptionThrown()
    }
}
