package com.example.userregistrationandlogin.service.impl;

import com.example.userregistrationandlogin.dto.UserActivationDto;
import com.example.userregistrationandlogin.entity.UserEntity;
import com.example.userregistrationandlogin.repository.UserRepository;
import com.example.userregistrationandlogin.service.UserActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActivationServiceImpl implements UserActivationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String verifyUser(String email, Integer activationCode) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity != null){

            if(userEntity.getIsActivated()){
                return "User already activated!";
            }
           if( activationCode== userEntity.getActivationCode()){
               userEntity.setIsActivated(true);
               userRepository.save(userEntity);
               return "account activated";
           }
           else {
               return "Invalid activation code! Please try again";
           }
        }
        return "User doesn't exist with the associated email address : " + email;
    }
}
