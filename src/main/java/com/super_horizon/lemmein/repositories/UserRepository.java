package com.super_horizon.lemmein.repositories;

import java.util.Optional;
import com.super_horizon.lemmein.models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {   
    
    //Optional was added in Java 8
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    
}