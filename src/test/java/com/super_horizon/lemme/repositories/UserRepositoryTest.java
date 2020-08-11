package com.super_horizon.lemme.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static org.junit.Assert.assertEquals;
import java.util.*;

import com.super_horizon.lemme.models.User;
import com.super_horizon.lemme.repositories.UserRepository;

@Repository
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;



}