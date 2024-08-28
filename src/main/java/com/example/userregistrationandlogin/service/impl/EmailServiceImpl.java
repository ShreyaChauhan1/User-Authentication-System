package com.example.userregistrationandlogin.service.impl;

import com.example.userregistrationandlogin.dto.EmailDetails;
import com.example.userregistrationandlogin.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
     private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;


    @Override
    public String sendEmail(EmailDetails email) throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setFrom(sender);
        helper.setTo(email.getRecipient());
        helper.setText(email.getBody(),true);
        helper.setSubject(email.getSubject());

        javaMailSender.send(message);
        return "Email sent successfully!";
    }
}
