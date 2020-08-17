package com.super_horizon.lemme.configuration;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import com.super_horizon.lemme.models.Customer;
import com.super_horizon.lemme.models.Discount;
import com.super_horizon.lemme.models.User;
import com.super_horizon.lemme.repositories.CustomerRepository;
import com.super_horizon.lemme.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.super_horizon.lemme.services.*;


@Configuration
//@EnableScheduling
public class Scheduler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    SmsService smsService;
    
    @Scheduled(fixedRate = 5000)
    public void sendDiscountEmails() {

        try {

            List<User> users = userRepository.findAll();

            // Temporary sender
            String sender = "+12513069663";
            
            for (User user : users) {
                Discount discount = user.getDiscount();
                if (discount.getAmount() != 0) {
                    switch (user.getDiscount().getBy()) {
                        case "VISITING_TIMES" : {
                            for (String ref: user.getCustomersRef()) {
                                Customer customer = customerRepository.findById(ref).get();
                                if (customer.getVisitCounter() >= user.getDiscount().getVisitTimes()) {
                                    String recipient = smsService.getFormattedPhone(customer.getPhoneNumber());
                                    String mess = "Hello " + customer.getFirstName() + ",\nYou have been visiting us for " + customer.getVisitCounter() + " times. Please visit us again because we have " + this.getDiscountAmount(discount.getType(), discount.getAmount()) + " discount for you.";
                                    smsService.sendSms(recipient, sender, mess);
                                }                              
                            }
                            break;
                        }
                        case "DOB" : {
                            for (String ref: user.getCustomersRef()) {
                                Customer customer = customerRepository.findById(ref).get();
                                LocalDate dob = LocalDate.parse(customer.getDob(), new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MM/dd/yyyy").toFormatter(Locale.US));
                                LocalDate now = LocalDate.now().plusDays(7);
                                
                                if (dob.getDayOfMonth() == now.getDayOfMonth() && dob.getMonthValue() == now.getMonthValue()) {
                                    String recipient = smsService.getFormattedPhone(customer.getPhoneNumber());
                                    String mess = "Hello " + customer.getFirstName() + ",\nYour birthday is on: " + customer.getDob() + " right?\nPlease visit us again because we have " + this.getDiscountAmount(discount.getType(), discount.getAmount()) + " discount for you.";
                                    smsService.sendSms(recipient, sender, mess);
                                }                                                  
                            }
                            break;
                        }
                        default : {
                            for (String ref: user.getCustomersRef()) {
                                Customer customer = customerRepository.findById(ref).get();
                                if (customer.getVisitCounter() >= user.getDiscount().getVisitTimes()) {
                                    String recipient = smsService.getFormattedPhone(customer.getPhoneNumber());
                                    String mess = "Hello " + customer.getFirstName() + ",\nYou have been visiting us for " + customer.getVisitCounter() + " times. Please visit us again because we have " + this.getDiscountAmount(discount.getType(), discount.getAmount()) + " discount for you.";
                                    smsService.sendSms(recipient, sender, mess);
                                }                       
                                LocalDate dob = LocalDate.parse(customer.getDob(), new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MM/dd/yyyy").toFormatter(Locale.US));
                                LocalDate now = LocalDate.now().plusDays(7);
                                if (dob.getDayOfMonth() == now.getDayOfMonth() && dob.getMonthValue() == now.getMonthValue()) {
                                    String recipient = smsService.getFormattedPhone(customer.getPhoneNumber());
                                    String mess = "Hello " + customer.getFirstName() + ",\nYour birthday is on: " + customer.getDob() + " right?\nPlease visit us again because we have " + this.getDiscountAmount(discount.getType(), discount.getAmount()) + " discount for you.";
                                    smsService.sendSms(recipient, sender, mess);
                                }           
                            }
                            break;
                        }
                    }
                }
                //smsService.sendSms(recipients, "+12513069663", mess);            
            }
        }
        catch (NoSuchElementException e) {
        }
        catch (NullPointerException e) {
        }
        catch (IllegalArgumentException e) {
        }
        catch (UnsupportedOperationException e) {
        }
        catch (ClassCastException e) {         
        }
        catch (DateTimeParseException e) {        
        }
        catch (DateTimeException e) {       
        }

    }

    private String getDiscountAmount(int type, int amount) {
        if (type == 0) {
            return "$" + amount;
        }
        return amount + "%";
    }
}