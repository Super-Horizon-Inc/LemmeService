package com.super_horizon.lemmein.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import com.super_horizon.lemmein.models.Customer;
import com.super_horizon.lemmein.services.CustomerService;
import com.super_horizon.lemmein.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/lemme/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<List<Customer>> showOrAdd(@RequestBody Map<String, String> query) {
        try {

            List<Customer> customers = customerService.showOrAdd(query);
            Customer customer = customers.get(0);

            if (customer.getIsNew() && customer.getEmail() != null) {
                emailService.sendEmail(customer.getEmail(), customer.getId());
            }

            return new ResponseEntity<> (customers, HttpStatus.CREATED);

        }
        catch (Exception ex) {
            return new ResponseEntity<> (null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value="/{id}/edit")
    public ModelAndView edit(@PathVariable String id) {

        try {
            Customer _customer = customerService.findById(id);
            var modelAndView = new ModelAndView();
            modelAndView.addObject("customer", _customer);
            modelAndView.setViewName("edit");

            return modelAndView;
        }
        catch (Exception e) {
            //return new ResponseEntity<> (null, HttpStatus.EXPECTATION_FAILED);
            return null;
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> update( @RequestBody Customer customer) {
        try {
            Customer _customer = customerService.update(customer);

            //log out automatically by making cookie invalid
            SecurityContextHolder.getContext().setAuthentication(null);

            return new ResponseEntity<> (_customer, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<> (null, HttpStatus.EXPECTATION_FAILED);
        }       
    }

    @PostMapping(value="/email")
    public String sendEmail(@RequestBody Customer customer) {
        
        if (!Objects.isNull(customer)) {

            Customer _customer = customerService.findById(customer.getId());
            _customer.setIsUpdated(false);
            customerService.save(_customer);
            
            emailService.sendEmail(customer.getEmail(), customer.getId());

            return "Email was sent successfully.\n\n Welcome in!";
        }
        return "Customer does not exist.";
    }

}