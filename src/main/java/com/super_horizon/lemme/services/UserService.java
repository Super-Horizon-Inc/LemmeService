package com.super_horizon.lemme.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.super_horizon.lemme.models.*;
import com.super_horizon.lemme.payload.response.*;
import com.super_horizon.lemme.repositories.*;
import com.super_horizon.lemme.security.jwt.*;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
	AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;


    //@Transactional
    public void addCustomerRef(String username, String customerRef) {
        try{
            User user = userRepository.findByUsername(username).get();           
            user.setCustomersRef(customerRef);
            userRepository.save(user);
        }
        catch (NoSuchElementException e) {
        } 
        catch (IllegalArgumentException e) {       
        }
    }

    public List<Customer> findCustomersByUsername(String username) {
        List<Customer> customers = new ArrayList<Customer>();
        try {
            User user = userRepository.findByUsername(username).get();     
            for (String ref : user.getCustomersRef()) {
                customers.add(customerRepository.findById(ref).get());
            }
        }
        catch (NoSuchElementException e) {
        }
        catch (IllegalArgumentException e) {
        }
        catch (UnsupportedOperationException e) { 
        }
        catch (ClassCastException e) {
        }
        catch (NullPointerException e) { 
        }

        return customers;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Authentication authenticateUser(String username, String password) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (AuthenticationException e) {
            return null;
        }
    }

    public void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public JwtResponse signin(String username, String password) {
        try{
            Authentication authentication = this.authenticateUser(username, password);       
            this.setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            Discount discount = userRepository.findByUsername(username).get().getDiscount();
            discount.setBy(EDiscountBy.valueOf(discount.getBy()).getValue().toString());
            return new JwtResponse(jwt, username, discount, this.findCustomersByUsername(username));
        }
        catch (NoSuchElementException e) {
        }
        catch (NullPointerException e) {
        }
        return null;
    }

    @Transactional
    public JwtResponse signup(String username, String password) {
        try {
            User user = new User(username, encoder.encode(password));
            userRepository.save(user);       
            return signin(username, password);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Transactional
    public void setting(User user) {
        try{
            Optional<User> optUser = userRepository.findByUsername(user.getUsername());
            User _user = optUser.get();
            user.getDiscount().setBy(EDiscountBy.valueOf(Integer.parseInt(user.getDiscount().getBy())).get().toString());
            _user.setDiscount(user.getDiscount());
            userRepository.save(_user);
        }
        catch (NoSuchElementException e) {
        }
        catch (NumberFormatException e) {
        }
        catch (IllegalArgumentException e) {
        }
        catch (NullPointerException e) {
        }
    }
    
    public boolean isNewCustomer(String username, String customerId) {
        try {
            User user = userRepository.findByUsername(username).get();
            return user.getCustomersRef().contains(customerId);
        }
        catch (NoSuchElementException e) {
        }
        catch (ClassCastException e) {
        }
        catch (NullPointerException e) {
        }
        return false;

    }
    
}