package com.super_horizon.lemme.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertEquals;
import com.super_horizon.lemme.repositories.*;
import com.super_horizon.lemme.models.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder encoder;


    public void shouldNotBeNewCustomer() {
        assertEquals("customer shouldn't be there", userService.isNewCustomer("A","B"), false);
    }

    public void shouldBeNewCustomer() {

        String customerId = customerRepository.save(new Customer()).getId();
        User user = new User("aaabbbc@bbb.ccc",encoder.encode("123456"));
        user.setCustomersRef(customerId);
        String username = userRepository.save(user).getUsername();

        assertEquals("customer should be there", userService.isNewCustomer(username,customerId), true);
    }
}