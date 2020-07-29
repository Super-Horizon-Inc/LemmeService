package com.super_horizon.lemmein.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class NoTokenException extends AuthenticationException {

    public NoTokenException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;   
    
}