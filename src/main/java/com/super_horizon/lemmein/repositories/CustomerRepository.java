package com.super_horizon.lemmein.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.super_horizon.lemmein.models.Customer;


@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>, IRepository<Customer> {
}