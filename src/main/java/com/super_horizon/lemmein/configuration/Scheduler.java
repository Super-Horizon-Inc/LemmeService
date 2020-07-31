package com.super_horizon.lemmein.configuration;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.context.annotation.Configuration;
import com.super_horizon.lemmein.models.*;
import java.util.*;

//import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import com.super_horizon.lemmein.repositories.UserRepository;
import com.super_horizon.lemmein.services.EmailService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import org.springframework.mail.SimpleMailMessage;


@Configuration
@EnableScheduling
public class Scheduler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;
    
    @Scheduled(fixedRate = 5000)
    public void sendDiscountEmails() {

        List<SimpleMailMessage> emails = new ArrayList<SimpleMailMessage>();
        List<User> users = userRepository.findAll();
        
        for (User user : users) {
            Discount discount = user.getDiscount();
            if (discount.getAmount() != 0) {
                switch (user.getDiscount().getBy()) {
                    case "VISITING_TIMES" : {
                        for (Customer customer: user.getCustomers()) {
                            if (!customer.getIsNew() && customer.getVisitCounter() >= user.getDiscount().getVisitTimes()) {
                                SimpleMailMessage email = new SimpleMailMessage();
                                
                                email.setTo(customer.getEmail());
                                email.setSubject("Lemmein");
                                email.setText("Hello " + customer.getFirstName() + ",\n\n You have been visiting us for " + customer.getVisitCounter() + " times. Please visit us again because we have " + this.getDiscountAmount(discount.getType(), discount.getAmount()) + " discount for you.");
                                emails.add(email);
                            }
                                                
                        }
                        break;
                    }

                    case "DOB" : {
                        for (Customer customer: user.getCustomers()) {
                            LocalDate dob = LocalDate.parse(customer.getDob(), new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MMMM d, yyyy").toFormatter(Locale.US));
                            LocalDate now = LocalDate.now().plusDays(7);
                            
                            if (!customer.getIsNew() && dob.getDayOfMonth() == now.getDayOfMonth() && dob.getMonthValue() == now.getMonthValue()) {
                                SimpleMailMessage email = new SimpleMailMessage();
                                
                                email.setTo(customer.getEmail());
                                email.setSubject("Lemmein");
                                email.setText("Hello " + customer.getFirstName() + ",\n\nYour birthday is on: " + customer.getDob() + " right?\nPlease visit us again because we have " + this.getDiscountAmount(discount.getType(), discount.getAmount()) + " discount for you.");
                                emails.add(email);
                            }
                                                
                        }
                        break;
                    }

                    default : {
                        for (Customer customer: user.getCustomers()) {
                            if (!customer.getIsNew() && customer.getVisitCounter() >= user.getDiscount().getVisitTimes()) {
                                SimpleMailMessage email = new SimpleMailMessage();
                                
                                email.setTo(customer.getEmail());
                                email.setSubject("Lemmein");
                                email.setText("Hello " + customer.getFirstName() + ",\n\nYou have been visiting us for " + customer.getVisitCounter() + " times. Please visit us again because we have " + this.getDiscountAmount(discount.getType(), discount.getAmount()) + " discount for you.");
                                emails.add(email);
                            }
                            
                            LocalDate dob = LocalDate.parse(customer.getDob(), new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MMMM d, yyyy").toFormatter(Locale.US));
                            LocalDate now = LocalDate.now().plusDays(7);

                            if (!customer.getIsNew() && dob.getDayOfMonth() == now.getDayOfMonth() && dob.getMonthValue() == now.getMonthValue()) {
                                SimpleMailMessage email = new SimpleMailMessage();
                                
                                email.setTo(customer.getEmail());
                                email.setSubject("Lemmein");
                                email.setText("Hello " + customer.getFirstName() + ",\n\nYour birthday is on: " + customer.getDob() + " right?\nPlease visit us again because we have " + this.getDiscountAmount(discount.getType(), discount.getAmount()) + " discount for you.");
                                emails.add(email);
                            }
                            
                        }
                        break;
                    }

                }
            }
            
        }
        emailService.sendEmails(emails);
       
    }

    private String getDiscountAmount(int type, int amount) {
        if (type == 0) {
            return "$" + amount;
        }
        return amount + "%";
    }
}