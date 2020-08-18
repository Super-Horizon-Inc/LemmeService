package com.super_horizon.lemme.configuration;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.Locale;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Value;

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
@EnableScheduling
public class Scheduler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    SMSService smsService;

    // Temporary sender
    @Value("${MessageSid}")
    private String messageSid;

    @Value("${NotifySid}")
    private String notifySid;
    
    
    @Scheduled(fixedRate = 5000)
    public void sendDiscountSms() {

        try {

            List<String> visitTimesPhones = new ArrayList<String>();
            List<String> dobPhones = new ArrayList<String>();

            List<User> users = userRepository.findAll();
            
            for (User user : users) {
                
                Discount discount = user.getDiscount();
                if (discount.getAmount() != 0) {
                    switch (user.getDiscount().getBy()) {
                        case "VISITING_TIMES" : {
                            for (String ref: user.getCustomersRef()) {
                                Customer customer = customerRepository.findById(ref).get();
                                if (customer.getVisitCounter() >= user.getDiscount().getVisitTimes()) {
                                    // String recipient = smsService.getFormattedPhone(customer.getPhoneNumber());
                                    // String mess = "Hello " + customer.getFirstName() + ",\nYou have been visiting us for " + customer.getVisitCounter() + " times. Please visit us again because we have " + this.getDiscountAmount(discount.getType(), discount.getAmount()) + " discount for you.";
                                    // smsService.sendSms(recipient, messageSid, mess);
                                    visitTimesPhones.add(smsService.getFormattedPhone(customer.getPhoneNumber()));
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
                                    // String recipient = smsService.getFormattedPhone(customer.getPhoneNumber());
                                    // String mess = "Hello " + customer.getFirstName() + ",\nYour birthday is on: " + customer.getDob() + " right?\nPlease visit us again because we have " + this.getDiscountAmount(discount.getType(), discount.getAmount()) + " discount for you.";
                                    // smsService.sendSms(recipient, messageSid, mess);
                                    dobPhones.add(smsService.getFormattedPhone(customer.getPhoneNumber()));
                                }                                                  
                            }
                            break;
                        }
                        default : {
                            for (String ref: user.getCustomersRef()) {
                                Customer customer = customerRepository.findById(ref).get();
                                if (customer.getVisitCounter() >= user.getDiscount().getVisitTimes()) {
                                    // String recipient = smsService.getFormattedPhone(customer.getPhoneNumber());
                                    // String mess = "Hello " + customer.getFirstName() + ",\nYou have been visiting us for " + customer.getVisitCounter() + " times. Please visit us again because we have " + this.getDiscountAmount(discount.getType(), discount.getAmount()) + " discount for you.";
                                    // smsService.sendSms(recipient, messageSid, mess);
                                    visitTimesPhones.add(smsService.getFormattedPhone(customer.getPhoneNumber()));                             
                                }                       
                                LocalDate dob = LocalDate.parse(customer.getDob(), new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MM/dd/yyyy").toFormatter(Locale.US));
                                LocalDate now = LocalDate.now().plusDays(7);
                                if (dob.getDayOfMonth() == now.getDayOfMonth() && dob.getMonthValue() == now.getMonthValue()) {
                                    // String recipient = smsService.getFormattedPhone(customer.getPhoneNumber());
                                    // String mess = "Hello " + customer.getFirstName() + ",\nYour birthday is on: " + customer.getDob() + " right?\nPlease visit us again because we have " + this.getDiscountAmount(discount.getType(), discount.getAmount()) + " discount for you.";
                                    // smsService.sendSms(recipient, messageSid, mess);
                                    dobPhones.add(smsService.getFormattedPhone(customer.getPhoneNumber()));
                                }           
                            }
                            break;
                        }
                    }    
                }
                //smsService.sendSms(recipients, "+12513069663", mess);  
            }
            // Binding: send many and no filter
            // Separate: Specific message
            smsService.sendSms(visitTimesPhones,notifySid,"Hi, How are you?");
            smsService.sendSms(dobPhones,notifySid,"Hi, How are you?");

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