package com.super_horizon.lemme.payload.request;

import javax.validation.constraints.*;
 

public class SignupRequest {

    @NotBlank
    @Size(min = 10, max = 50)
    @Email
    private String username;
 
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
    
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

}