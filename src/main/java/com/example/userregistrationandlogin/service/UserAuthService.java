package com.example.userregistrationandlogin.service;

import com.example.userregistrationandlogin.dto.UserLogin;
import com.example.userregistrationandlogin.dto.UserRegistration;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

public interface UserAuthService {
    String registerUser(UserRegistration user);
//    String loginUser(UserLogin user);
    String loginUser(UserLogin user, HttpServletRequest request) throws MessagingException;
}
