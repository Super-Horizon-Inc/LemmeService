package com.super_horizon.lemme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendEmail(String email, String id) {

        try{
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject("Lemmein");
            msg.setText("Please click on the link below to confirm\n" + "http://localhost:8080/lemme/customers/" + id + "/edit");
            javaMailSender.send(msg);                   
            return true;
        }     
        catch (MailParseException e) {
        }
        catch (MailAuthenticationException e) {        
        }
        catch (MailSendException e) {          
        }
        catch (MailException e) {
        }
        return false;
        
    }

}