package com.super_horizon.lemme.security.jwt;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.super_horizon.lemme.security.services.UserDetailsImpl;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${jwtSecret}")
	private String jwtSecret;

	@Value("${jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

        try {

            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
            return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();

        }
        catch (ClassCastException e) {
            return null;
		}	
			
	}

	public String getUserNameFromJwtToken(String token) {

        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        }
        catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return null;
		
	}

	public boolean validateJwtToken(String authToken) {

		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
        } 
        catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
        } 
        catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
        } 
        catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
        } 
        catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
        } 
        catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
		
	}

}