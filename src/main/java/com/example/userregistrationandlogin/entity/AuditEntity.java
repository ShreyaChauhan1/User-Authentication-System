package com.example.userregistrationandlogin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user-audit-table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private LocalDateTime loginTime;
    private String loginResult; // "SUCCESS" or "FAILURE"
    private String ipAddress;
    private String deviceDetails;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
