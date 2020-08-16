package com.super_horizon.lemme.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class NoTokenException extends AuthenticationException {

  private static final long serialVersionUID = 1L;

  public NoTokenException(String msg) {
    super(msg);
  }
  
}