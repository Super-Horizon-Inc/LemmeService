package com.super_horizon.lemme.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;


@Service
public class SmsService {

    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID =
            "ACd0e91293b147e766d070dce032d51c12";
    public static final String AUTH_TOKEN =
            "a46df5d2e23c55d4f1d91f544d3199f8";

    public SmsService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendSms(String recipient, String sender, String mess) {

        Message message = Message.creator(new PhoneNumber(recipient), // to
                                            new PhoneNumber(sender), // from
                                            mess).create();

        System.out.println(message.getSid());

    }

    public String getFormattedPhone(String phone) {
        return "+1" + phone.replaceAll("[^0-9]", "");
    }
}