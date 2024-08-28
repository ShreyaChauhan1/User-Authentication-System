package com.example.userregistrationandlogin.repository;

import com.example.userregistrationandlogin.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

 UserEntity findByEmail(String email);
}
