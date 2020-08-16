package com.super_horizon.lemme.services;

import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.time.LocalDate;
import java.util.Locale;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatterBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.super_horizon.lemme.models.*;
import com.super_horizon.lemme.repositories.CustomerRepository;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Customer getOrAdd(Map<String, String> query) {

        Customer customer = null;

        try{

            String username = query.get("username");
            query.remove("username");
            customer = customerRepository.findOrCreate(query);
            
            if (!userService.isNewCustomer(username, customer.getId())) {
                userService.addCustomerRef(username, customer.getId());
            }               
            
        }
        catch (ClassCastException e) {
        }
        catch (NullPointerException e) {          
        }
        catch (UnsupportedOperationException e) {       
        }
        catch (IndexOutOfBoundsException e) {
        }
        return customer;
  
    }

    @Transactional
    public Customer update(Customer customer) {

        Customer _customer = null;
        try{

            _customer = customerRepository.findById(customer.getId()).get();
            
            String phoneNumberForm = customer.getPhoneNumber();
            String phoneNumber = phoneNumberForm.length() > 14 ? phoneNumberForm.split("\\+1 ")[1] : phoneNumberForm;

            _customer.setPhoneNumber(phoneNumber);   
            _customer.setEmail(customer.getEmail());

            // if (customer.getDob().contains("/")) {
            //     LocalDate dob = LocalDate.parse(customer.getDob(), new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MM/dd/yyyy").toFormatter(Locale.US));
            //     String dobString = dob.getMonth() + " " + dob.getDayOfMonth() + ", " + dob.getYear();
            //     _customer.setDob(dobString);
            // }
            // else {
            //     _customer.setDob(customer.getDob());
            // }
            _customer.setDob(customer.getDob());

            _customer.setFirstName(customer.getFirstName());
            _customer.setLastName(customer.getLastName());
            _customer.setVisitCounter(_customer.getVisitCounter() + 1);
            
            customerRepository.save(_customer);

        }
        catch (PatternSyntaxException e) {
        }
        catch (NullPointerException e) {
        }
        catch (DateTimeParseException e) {    
        }
        catch (IllegalArgumentException e) {
        }

        return _customer;

    }

}