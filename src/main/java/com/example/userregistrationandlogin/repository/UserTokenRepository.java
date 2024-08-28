package com.example.userregistrationandlogin.repository;

import com.example.userregistrationandlogin.entity.UserEntity;
import com.example.userregistrationandlogin.entity.UserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserTokenEntity, Long> {
    UserTokenEntity findByConfirmationToken(String confirmationToken);
    UserTokenEntity findByUser(UserEntity user);
}
