package com.super_horizon.lemme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.junit.Assert.assertEquals;

@Service
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    public void shouldNotSendEmail() {
        assertEquals("Send invalid email address", emailService.sendEmail("A","B"), false);
    }

    public void shouldSendEmail() {
        assertEquals("Send invalid email address", emailService.sendEmail("Bahram.afsharipoor.1992@gmail.com","B"), true);
    }
}