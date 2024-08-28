package com.example.userregistrationandlogin.mapper;

import com.example.userregistrationandlogin.dto.UserRegistration;
import com.example.userregistrationandlogin.entity.UserEntity;

public class UserMapper {

    // Convert UserRegistration DTO to UserEntity
    public static UserEntity mapToUser(UserRegistration user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setIsActivated(user.getIsActivated());
        userEntity.setActivationCode(user.getActivationCode());
        return userEntity;
    }

    // Convert UserEntity to UserRegistration DTO
    public static UserRegistration mapToUserRegistrationDto(UserEntity user) {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setId(user.getId());
        userRegistration.setFirstName(user.getFirstName());
        userRegistration.setLastName(user.getLastName());
        userRegistration.setEmail(user.getEmail());
        userRegistration.setPassword(user.getPassword());
        userRegistration.setIsActivated(user.getIsActivated());
        userRegistration.setActivationCode(user.getActivationCode());
        return userRegistration;
    }
}
