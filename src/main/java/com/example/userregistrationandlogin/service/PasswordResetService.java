package com.example.userregistrationandlogin.service;

import com.example.userregistrationandlogin.dto.UpdatePassword;
import jakarta.mail.MessagingException;

public interface PasswordResetService {
    String resetPassword(String token, String password) throws  MessagingException;
    String forgotPassword(String userName) throws MessagingException;
    String updatePassword(String emailAddress, UpdatePassword updatePassword) throws MessagingException;
}
