package com.super_horizon.lemme.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import com.super_horizon.lemme.models.Customer;
import com.super_horizon.lemme.services.*;


@RestController
@RequestMapping("/lemme/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> getOrAdd(@RequestBody Map<String, String> query) {
        Customer customer = null;
        try {
            customer = customerService.getOrAdd(query);
            return new ResponseEntity<> (customer, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<> (null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> update(@RequestBody Customer customer) {
        try {
            Customer _customer = customerService.update(customer);            
            return new ResponseEntity<> (_customer, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<> (null, HttpStatus.EXPECTATION_FAILED);
        }       
    }

}