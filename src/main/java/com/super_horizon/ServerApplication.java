package com.super_horizon;

import com.super_horizon.lemmein.repositories.CustomerRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = CustomerRepository.class)
public class ServerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServerApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
    }

}
