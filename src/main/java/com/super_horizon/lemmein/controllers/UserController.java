package com.super_horizon.lemmein.controllers;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.ObjenesisException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.super_horizon.lemmein.models.*;
import com.super_horizon.lemmein.payload.request.*;
import com.super_horizon.lemmein.payload.response.*;
import com.super_horizon.lemmein.repositories.*;
import com.super_horizon.lemmein.security.services.*;
import com.super_horizon.lemmein.security.jwt.*;
import com.super_horizon.lemmein.services.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/lemme/user/auth")
public class UserController {

    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
    UserRepository userRepository;

	@Autowired
    PasswordEncoder encoder;
    
    @Autowired
    CustomerService customerService;

	@Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    
    @PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // if (!userRepository.existsByUsername(loginRequest.getUsername())) {
        //     return ResponseEntity
        //             .badRequest()
        //             .body(new MessageResponse("Error: " + loginRequest.getUsername() + " does not exist."));
        // }
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));       
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Discount discount = userRepository.findByUsername(userDetails.getUsername()).get().getDiscount();
        discount.setBy(EDiscountBy.valueOf(discount.getBy()).getValue().toString());
        
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), discount, userService.findCustomersByUsername(userDetails.getUsername())));
	}


	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: " + signUpRequest.getUsername() + " is already taken!"));
		}

		User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
        
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(signUpRequest.getUsername());
        loginRequest.setPassword(signUpRequest.getPassword());

        return this.authenticateUser(loginRequest);      
    }

    
    @PostMapping("/logout")
    public ResponseEntity<?> logoutCustomer(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(null);
            return ResponseEntity.ok(new MessageResponse("Logout successfully."));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Wrong password."));
        }       
    }

    
    @PutMapping("/setting")
    public ResponseEntity<?> editSetting(@RequestBody User user) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            
            Optional<User> optUser = userRepository.findByUsername(user.getUsername());
            User _user = optUser.get();

            user.getDiscount().setBy(EDiscountBy.valueOf(Integer.parseInt(user.getDiscount().getBy())).get().toString());
            _user.setDiscount(user.getDiscount());

            userRepository.save(_user);
            return ResponseEntity.ok(new MessageResponse("Setting saved successfully."));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Wrong password."));
        }
    }
   
}