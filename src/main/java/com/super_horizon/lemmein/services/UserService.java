package com.super_horizon.lemmein.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.super_horizon.lemmein.repositories.UserRepository;
import com.super_horizon.lemmein.models.*;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addCustomer(String username, Customer customer) {
        try{
            Optional<User> user = userRepository.findByUsername(username);
            
            user.get().setCustomers(customer);
            userRepository.save(user.get());
        }
        catch (Exception e) {
            System.out.println("Exception in UserService.");
        }
    }

    public List<Customer> findCustomersByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.get().getCustomers();
    }
    
}