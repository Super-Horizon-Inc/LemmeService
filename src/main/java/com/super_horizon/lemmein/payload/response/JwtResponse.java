package com.super_horizon.lemmein.payload.response;

import java.util.*;
import com.super_horizon.lemmein.models.*;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String id;
    private String username;
    private Optional<List<Customer>> customers;
    private Discount discount;

    public JwtResponse(String accessToken, String id, String username, Discount discount, Optional<List<Customer>> customers) {
		this.token = accessToken;
		this.id = id;
        this.username = username;
        this.discount = discount;
        this.customers = customers;
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

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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
    
    public Optional<List<Customer>> getCustomers() {
		return this.customers;
	}

	public void setCustomers(Optional<List<Customer>> customers) {
		this.customers = customers;
	}

}