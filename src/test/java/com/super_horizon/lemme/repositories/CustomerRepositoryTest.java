package com.super_horizon.lemme.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static org.junit.Assert.assertEquals;
import java.util.*;
import com.super_horizon.lemme.models.Customer;

@Repository
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    public void shouldReturnNull() {

        Map<String, String> query = null;
        Customer customer = customerRepository.findOrCreate(query);
        assertEquals(Objects.isNull(customer), true);

    }

}