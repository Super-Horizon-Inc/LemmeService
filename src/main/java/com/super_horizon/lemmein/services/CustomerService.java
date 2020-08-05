package com.super_horizon.lemmein.services;

import java.util.*;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.InvocationTargetException;

import com.super_horizon.lemmein.repositories.CustomerRepository;
import com.super_horizon.lemmein.models.*;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public List<Customer> showOrAdd(Map<String, String> query) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

            String username = query.get("username");
            query.remove("username");
            List<Customer> customers = customerRepository.findOrCreate(query);
            Customer customer = customers.get(0);
            if (customer.getIsNew()) {
                if (customer.getEmail() != null) {
                    emailService.sendEmail(customer.getEmail(), customer.getId());
                }
                if (!userService.isNewCustomer(username, customer.getId())) {
                    userService.addCustomerRef(username, customer.getId());
                }               
            }
            
            return customers;    
    }

    public Customer findById(String id) {

        Customer _customer = customerRepository.findById(id).get();

        if (!Objects.isNull(_customer)) {
            return _customer;
        }

        throw new NullPointerException();       
    }

    @Transactional
    public Customer update(Customer customer) {

        Customer _customer = this.findById(customer.getId());
          
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

        return _customer;
    }

    public List<Customer> searchAll() {

        List<Customer> customers = customerRepository.findAll();
        
        if (!customers.isEmpty()) {
            return customers;
        }

        throw new NullPointerException();
    }

    @Transactional
    public void sendEmail(Customer customer) {
        Customer _customer = findById(customer.getId());
        _customer.setIsUpdated(false);
        customerRepository.save(_customer);
        emailService.sendEmail(customer.getEmail(), customer.getId());
    }

}