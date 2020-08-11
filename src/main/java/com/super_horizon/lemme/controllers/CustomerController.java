package com.super_horizon.lemme.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.super_horizon.lemme.models.Customer;
import com.super_horizon.lemme.services.*;


@RestController
@RequestMapping("/lemme/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    

    @PostMapping
    public ResponseEntity<List<Customer>> showOrAdd(@RequestBody Map<String, String> query) {
        List<Customer> customers = new ArrayList<Customer>();
        try {
            customers = customerService.showOrAdd(query);
            return new ResponseEntity<> (customers, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<> (customers, HttpStatus.EXPECTATION_FAILED);
        }
    }


    @GetMapping(value="/{id}/edit")
    public ModelAndView edit(@PathVariable String id) {
        var modelAndView = new ModelAndView();
        try {
            Customer _customer = customerService.findById(id);           
            modelAndView.addObject("customer", _customer);
            modelAndView.setViewName("edit");            
        }
        catch (Exception e) {
            //TODO implement catch
        }
        return modelAndView;
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> update( @RequestBody Customer customer) {
        try {
            Customer _customer = customerService.update(customer);            
            return new ResponseEntity<> (_customer, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<> (null, HttpStatus.EXPECTATION_FAILED);
        }       
    }

    @PostMapping(value="/email")
    public String sendEmail(@RequestBody Customer customer) {       
        try{ 
            if(!Objects.isNull(customer)) {
                customerService.sendEmail(customer);
                return "Email was sent successfully.\n\n Welcome in!";
            }
            return "Customer does not exist.";
        }
        catch (Exception e) {
            return "Something went wrong.";
        }
        
    }
}