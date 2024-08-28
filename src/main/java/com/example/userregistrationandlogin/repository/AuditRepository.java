package com.example.userregistrationandlogin.repository;

import com.example.userregistrationandlogin.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
    List<AuditEntity> findByUserId(Long userId);
}
