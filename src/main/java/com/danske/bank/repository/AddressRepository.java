package com.danske.bank.repository;

import com.danske.bank.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(nativeQuery = true, value = "SELECT ad.id " +
            "FROM address ad " +
            "WHERE ad.customer_Id = :customerId")
    List<Long> findAllByCustomerId(@Param("customerId") Long customerId);
}
