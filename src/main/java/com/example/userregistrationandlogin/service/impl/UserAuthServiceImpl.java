package com.example.userregistrationandlogin.service.impl;

import com.example.userregistrationandlogin.dto.EmailDetails;
import com.example.userregistrationandlogin.dto.UserLogin;
import com.example.userregistrationandlogin.dto.UserRegistration;
import com.example.userregistrationandlogin.entity.UserEntity;
import com.example.userregistrationandlogin.entity.UserTokenEntity;
import com.example.userregistrationandlogin.mapper.UserMapper;
import com.example.userregistrationandlogin.repository.UserRepository;
import com.example.userregistrationandlogin.repository.UserTokenRepository;
import com.example.userregistrationandlogin.service.AuditService;
import com.example.userregistrationandlogin.service.UserAuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private AuditService auditService;


    @Override
    public String registerUser(UserRegistration user) {
        UserEntity userExists = userRepository.findByEmail(user.getEmail());
        if (userExists != null) {
         return "User already exists with the email address , please use a new/unique email address!";
        }
        UserEntity userEntity = UserMapper.mapToUser(user);
        userRepository.save(userEntity);
        return "User Registered successfully!";
    }

    @Override
    public String loginUser(UserLogin user, HttpServletRequest request) throws MessagingException {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        if (userEntity != null) {
            String loginResult;
            if (!userEntity.getIsActivated()) {
                loginResult = "Account is not active yet. Please check your email.";
                String activationUrl = "http://localhost:9999/api/user/activate?email=" + user.getEmail() + "&activationCode="+ userEntity.getActivationCode();
                String body = "<h1>Click on the link below to activate your account : </h1>" + "<a href='"+ activationUrl +"'> Activate account </a>";
                String subject = "Activate your account!";
                EmailDetails email = new EmailDetails(user.getEmail(),body, subject);
                emailService.sendEmail(email);
            } else if (passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
                loginResult = "Login successfully";
            } else {
                loginResult = "Incorrect password! \nPlease try again.";
            }

            // Log the audit for login attempt
            auditService.log(
                    userEntity,
                    LocalDateTime.now(),
                    loginResult,
                    request, // Extract IP address from request
                    request.getHeader("Sec-Ch-Ua") // Extract device details from request
            );

            return loginResult;
        }
        return "User not found with the email: " + user.getEmail(); // or handle accordingly
    }


}
