package com.super_horizon.lemme.payload.response;

import java.util.*;

import com.super_horizon.lemme.models.*;


public class JwtResponse {
	private String token;
	private String type = "Bearer";
    private String username;
    private List<Customer> customers;
    private Discount discount;
    private String userId;

    
    public JwtResponse(String accessToken, String username, Discount discount, List<Customer> customers, String userId) {
		this.token = accessToken;
        this.username = username;
        this.discount = discount;
        this.customers = customers;
        this.userId = userId;
	}

	public String getAccessToken() {
		return this.token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return this.type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
    }

    public Discount getDiscount() {
		return this.discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
    
    public List<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
    }
    
    public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}