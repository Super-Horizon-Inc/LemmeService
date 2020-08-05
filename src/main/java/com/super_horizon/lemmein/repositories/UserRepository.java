package com.super_horizon.lemmein.repositories;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.super_horizon.lemmein.models.*;


@Repository
public interface UserRepository extends MongoRepository<User, String> {   
    
    //Optional was added in Java 8
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    
}