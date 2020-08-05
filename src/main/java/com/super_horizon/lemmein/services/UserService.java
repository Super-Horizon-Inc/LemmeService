package com.super_horizon.lemmein.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.super_horizon.lemmein.repositories.*;
import com.super_horizon.lemmein.models.*;
import com.super_horizon.lemmein.payload.response.*;
import com.super_horizon.lemmein.security.jwt.*;


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
            Optional<User> user = userRepository.findByUsername(username);
            
            user.get().setCustomersRef(customerRef);
            userRepository.save(user.get());
        }
        catch (Exception e) {
            System.out.println("Exception in UserService.");
        }
    }

    public List<Customer> findCustomersByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        List<Customer> customers = new ArrayList<Customer>();
        for (String ref : user.get().getCustomersRef()) {
            customers.add(customerRepository.findById(ref).get());
        }
        return customers;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Authentication authenticateUser(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    public void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public JwtResponse signin(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));       
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        Discount discount = userRepository.findByUsername(username).get().getDiscount();
        discount.setBy(EDiscountBy.valueOf(discount.getBy()).getValue().toString());
        return new JwtResponse(jwt, username, discount, findCustomersByUsername(username));
    }

    @Transactional
    public JwtResponse signup(String username, String password) {
        User user = new User(username, encoder.encode(password));
        userRepository.save(user);       
        return signin(username, password);
    }

    @Transactional
    public void setting(User user) {
        Optional<User> optUser = userRepository.findByUsername(user.getUsername());
        User _user = optUser.get();
        user.getDiscount().setBy(EDiscountBy.valueOf(Integer.parseInt(user.getDiscount().getBy())).get().toString());
        _user.setDiscount(user.getDiscount());
        userRepository.save(_user);
    }
    
    public boolean isNewCustomer(String username, String customerId) {
        User user = userRepository.findByUsername(username).get();
        return user.getCustomersRef().contains(customerId);
    }
}