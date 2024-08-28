package com.example.userregistrationandlogin.service;

import com.example.userregistrationandlogin.dto.EmailDetails;
import jakarta.mail.MessagingException;

public interface EmailService {

    String sendEmail(EmailDetails email) throws MessagingException;
}
