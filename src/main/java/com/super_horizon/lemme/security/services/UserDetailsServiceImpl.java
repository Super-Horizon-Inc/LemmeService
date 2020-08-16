package com.super_horizon.lemme.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import com.super_horizon.lemme.models.User;
import com.super_horizon.lemme.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username).get();
                    
            return UserDetailsImpl.build(user);
        }
        catch (NoSuchElementException e) {
            //.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        }
        return null;	
	}

}