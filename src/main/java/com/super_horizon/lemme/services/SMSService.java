package com.super_horizon.lemme.services;

import java.util.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.notify.v1.service.Notification;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


@Service
public class SMSService {

    // Must use non-static setter to inject value into static variable
    private static String AccountSid;
    @Value("${AccountSid}")
    public void setAccountSid(String accountSid) {
        AccountSid = accountSid;
    }

    private static String AuthToken;
    @Value("${AuthToken}")
    public void setauthToken(String authToken) {
        AuthToken = authToken;
    }

    public void sendSms(List<String> phones, String sender, String mess) {

        Twilio.init(AccountSid, AuthToken);

        List<String> recipients = new ArrayList<String>();
        for(String phone : phones) {
            recipients.add("{\"binding_type\":\"sms\",\"address\":\"" + phone +"\"}");
        }
        
        Notification notification = Notification.creator(sender)
                                                .setBody(mess)
                                                .setToBinding(recipients)
                                                .create();
        System.out.println(notification.getSid());

        
        // Message message = Message.creator(new PhoneNumber(recipient), // to
        //                                     //new PhoneNumber(sender), // from
        //                                     sender,
        //                                     mess).create();
        // System.out.println(message.getSid());

    }

    public String getFormattedPhone(String phone) {
        return "+1" + phone.replaceAll("[^0-9]", "");
    }
}