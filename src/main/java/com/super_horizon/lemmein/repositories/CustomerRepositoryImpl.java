package com.super_horizon.lemmein.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.super_horizon.lemmein.models.*;


public class CustomerRepositoryImpl extends RepositoryImpl<Customer> {

    private final MongoTemplate mongoTemplate;

    
    @Autowired
    public CustomerRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate, Customer.class);
        this.mongoTemplate = mongoTemplate;        
    }

}