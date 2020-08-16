package com.super_horizon.lemme.repositories;

import java.util.Optional;

import com.super_horizon.lemme.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {   
    
    //Optional was added in Java 8
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    
}