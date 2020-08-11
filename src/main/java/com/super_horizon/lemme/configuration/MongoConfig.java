package com.super_horizon.lemme.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.super_horizon.lemme.conditions.DatabaseType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.super_horizon.lemme.repositories")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Bean
    @DatabaseType("production")
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Override
    @DatabaseType("production")
    protected String getDatabaseName() {
        return "lemmein";
    }

    @Override
    @DatabaseType("production")
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://super:horizon@127.0.0.1:27017/lemmein");
    }
}