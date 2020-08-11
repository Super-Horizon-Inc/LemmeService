package com.super_horizon.lemme.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static org.junit.Assert.assertEquals;
import java.util.*;

import com.super_horizon.lemme.models.Customer;
import com.super_horizon.lemme.repositories.CustomerRepository;

@Repository
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    public void shouldReturnEmptyList() {

        Map<String, String> query = null;
        List<Customer> customers = customerRepository.findOrCreate(query);
        assertEquals(customers.isEmpty(), true);

    }

}