package com.chickenfarms.chickenfarms.repository;

import com.chickenfarms.chickenfarms.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
