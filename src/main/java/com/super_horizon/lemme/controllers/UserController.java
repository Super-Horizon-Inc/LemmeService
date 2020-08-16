package com.super_horizon.lemme.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.super_horizon.lemme.models.*;
import com.super_horizon.lemme.payload.request.*;
import com.super_horizon.lemme.payload.response.*;
import com.super_horizon.lemme.services.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/lemme/user/auth")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        if (!userService.existsByUsername(loginRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: " + loginRequest.getUsername() + " does not exist."));
        }

        JwtResponse jwtResponse = userService.signin(loginRequest.getUsername(), loginRequest.getPassword());      
        return ResponseEntity.ok(jwtResponse);
	}
 
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: " + signUpRequest.getUsername() + " is already taken!"));
        }

        JwtResponse jwtResponse = userService.signup(signUpRequest.getUsername(), signUpRequest.getPassword());
        return ResponseEntity.ok(jwtResponse);            
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutCustomer(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

            //need to handle correctly
            if (userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword()) == null) {
                throw new Exception();
            }

            userService.setAuthentication(null);
            return ResponseEntity.ok(new MessageResponse("Logout successfully."));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Wrong password."));
        }       
    }

    @PutMapping("/setting")
    public ResponseEntity<?> editSetting(@RequestBody User user) {

        try {
            userService.authenticateUser(user.getUsername(), user.getPassword());

             //need to handle correctly
             if (userService.authenticateUser(user.getUsername(), user.getPassword()) == null) {
                throw new Exception();
            }

            userService.setting(user);
            return ResponseEntity.ok(new MessageResponse("Setting saved successfully."));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Wrong password."));
        }
    }
   
}