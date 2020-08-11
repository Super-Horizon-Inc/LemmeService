package com.super_horizon;

import com.super_horizon.lemme.repositories.CustomerRepositoryTest;
import com.super_horizon.lemme.services.EmailService;
import com.super_horizon.lemme.services.EmailServiceTest;
import com.super_horizon.lemme.services.UserServiceTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    private CustomerRepositoryTest customerRepositoryTest;

    @Autowired
    private EmailServiceTest emailServiceTest;

    @Autowired
    private UserServiceTest userServiceTest;

	@Test
	void contextLoads() {
        // customerRepositoryTest.shouldReturnEmptyList();
        // emailServiceTest.shouldNotSendEmail();
        // emailServiceTest.shouldSendEmail();
        // userServiceTest.shouldNotBeNewCustomer();
        // userServiceTest.shouldBeNewCustomer();
	}

}
