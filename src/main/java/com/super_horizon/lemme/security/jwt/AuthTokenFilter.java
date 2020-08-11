package com.super_horizon.lemme.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.super_horizon.lemme.security.services.UserDetailsServiceImpl;


public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            if (!request.getRequestURI().contains("auth") && !request.getRequestURI().contains("update") && !request.getRequestURI().contains("edit") 
                && !request.getRequestURI().contains("images") && !request.getRequestURI().contains("favicon.ico")) {

                String jwt = parseJwt(request);

                if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                    String username = jwtUtils.getUserNameFromJwtToken(jwt);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                else {
                    new AuthEntryPointJwt().commence(request, response, new NoTokenException("No token provided."));
                }
            }
            filterChain.doFilter(request, response);
        }
        catch (ServletException e) {
        }
        catch (IOException e) {
        }
        catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        
    }

    private String parseJwt(HttpServletRequest request) {
        try {
            String headerAuth = request.getHeader("Authorization");
            if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
                return headerAuth.substring(7, headerAuth.length());
            }
            return null;
        }
        catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    
}