package com.example.userregistrationandlogin.service;

import com.example.userregistrationandlogin.entity.AuditEntity;
import com.example.userregistrationandlogin.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditService {
    void log(UserEntity user, LocalDateTime loginTime, String loginResult, HttpServletRequest request, String deviceDetails);
    List<AuditEntity> getAuditsForUser(Long userId);
}