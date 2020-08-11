package com.super_horizon.lemme.services;

import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.InvocationTargetException;

import com.super_horizon.lemme.models.*;
import com.super_horizon.lemme.repositories.CustomerRepository;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public List<Customer> showOrAdd(Map<String, String> query) {
        List<Customer> customers = new ArrayList<Customer>();
        try{
            String username = query.get("username");
            query.remove("username");
            customers = customerRepository.findOrCreate(query);
            
            Customer customer = customers.get(0);
            if (customer.getIsNew()) {
                if (customer.getEmail() != null) {
                    emailService.sendEmail(customer.getEmail(), customer.getId());
                }
                if (!userService.isNewCustomer(username, customer.getId())) {
                    userService.addCustomerRef(username, customer.getId());
                }               
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
        return customers;
  
    }

    public Customer findById(String id) {
        Customer _customer = null;
        try{
            _customer = customerRepository.findById(id).get();
        }
        catch (IllegalArgumentException e) {
        }
        catch (NoSuchElementException e) {
        }
        return _customer;
    }

    @Transactional
    public Customer update(Customer customer) {
        Customer _customer = null;
        try{
            _customer = this.findById(customer.getId());
            
            String phoneNumberForm = customer.getPhoneNumber();
            String phoneNumber = phoneNumberForm.length() > 14 ? phoneNumberForm.split("\\+1 ")[1] : phoneNumberForm;

            _customer.setPhoneNumber(phoneNumber);   
            _customer.setEmail(customer.getEmail());
            _customer.setIsNew(false);

            if (customer.getDob().contains("-")) {
                LocalDate dob = LocalDate.parse(customer.getDob());
                String dobString = dob.getMonth() + " " + dob.getDayOfMonth() + ", " + dob.getYear();
                _customer.setDob(dobString);
            }
            else {
                _customer.setDob(customer.getDob());
            }

            _customer.setFirstName(customer.getFirstName());
            _customer.setLastName(customer.getLastName());

            if (!_customer.getIsUpdated()) {
                _customer.setVisitCounter(_customer.getVisitCounter()+1);
            }
            
            _customer.setIsUpdated(true);

            customerRepository.save(_customer);
        }
        catch (PatternSyntaxException e) {
        }
        catch (NullPointerException e) {
        }
        catch (DateTimeParseException e) {    
        }
        return _customer;
    }

    public List<Customer> searchAll() {

        List<Customer> customers = customerRepository.findAll();
        return customers;
        
    }

    @Transactional
    public void sendEmail(Customer customer) {
        try{
            Customer _customer = findById(customer.getId());
            _customer.setIsUpdated(false);
            customerRepository.save(_customer);
            emailService.sendEmail(customer.getEmail(), customer.getId());
        }
        catch (IllegalArgumentException e) {
        }
    }

}