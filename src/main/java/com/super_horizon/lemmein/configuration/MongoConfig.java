package com.super_horizon.lemmein.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.super_horizon.lemmein.repositories")
public class MongoConfig extends AbstractMongoClientConfiguration {

    public static MongoClient client = null;

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Override
    protected String getDatabaseName() {
        return "lemmein";
    }

    @Override
    public MongoClient mongoClient() {
        client = MongoClients.create("mongodb://super:horizon@127.0.0.1:27017/lemmein");
        return client;
    }
}