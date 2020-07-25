package com.super_horizon.lemmein.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.super_horizon.lemmein.models.*;
import com.super_horizon.lemmein.payload.request.*;
import com.super_horizon.lemmein.payload.response.*;
import com.super_horizon.lemmein.repositories.*;
import com.super_horizon.lemmein.security.services.*;
import com.super_horizon.lemmein.security.jwt.*;
import com.super_horizon.lemmein.services.CustomerService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/lemme/user")
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
    
    @PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
		
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), customerService.findAllByUsername(userDetails.getUsername())));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
        
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(signUpRequest.getUsername());
        loginRequest.setPassword(signUpRequest.getPassword());

        return this.authenticateUser(loginRequest);      
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok(new MessageResponse("Logout successfully."));
    }
   
}