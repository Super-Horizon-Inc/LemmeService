package com.super_horizon.lemmein.repositories;

import com.super_horizon.lemmein.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>, IRepository<Customer> {
}