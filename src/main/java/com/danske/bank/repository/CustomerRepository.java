package com.danske.bank.repository;

import com.danske.bank.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(nativeQuery = true, value = "SELECT c.* " +
            "FROM customer c " +
            "JOIN address a ON c.id = a.customer_Id " +
            "WHERE c.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR c.email LIKE CONCAT('%', :searchTerm, '%')" +
            "OR c.lastname LIKE CONCAT('%', :searchTerm, '%')" +
            "OR c.phone_number LIKE CONCAT('%', :searchTerm, '%')" +
            "OR c.customer_type LIKE CONCAT('%', :searchTerm, '%')" +
            "OR a.country LIKE CONCAT('%', :searchTerm, '%')" +
            "OR a.address_line LIKE CONCAT('%', :searchTerm, '%')" +
            "OR a.zip_code LIKE CONCAT('%', :searchTerm, '%')")
    List<Customer> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
}
