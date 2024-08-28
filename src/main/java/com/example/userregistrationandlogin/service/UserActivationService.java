package com.example.userregistrationandlogin.service;

import com.example.userregistrationandlogin.dto.UserActivationDto;

public interface UserActivationService {
    String verifyUser(String email, Integer activationCode);
}
