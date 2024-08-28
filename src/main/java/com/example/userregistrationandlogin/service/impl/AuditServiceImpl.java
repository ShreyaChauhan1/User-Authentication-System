package com.example.userregistrationandlogin.service.impl;

import com.example.userregistrationandlogin.entity.AuditEntity;
import com.example.userregistrationandlogin.entity.UserEntity;
import com.example.userregistrationandlogin.repository.AuditRepository;
import com.example.userregistrationandlogin.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {
    @Autowired
    private AuditRepository auditRepository;

    @Override
    public void log(UserEntity user, LocalDateTime loginTime, String loginResult, HttpServletRequest request, String deviceDetails) {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setUser(user);
        auditEntity.setLoginTime(loginTime);
        auditEntity.setLoginResult(loginResult);
        auditEntity.setIpAddress(getClientIpAddress(request)); // Retrieve client IP address correctly
        auditEntity.setDeviceDetails(deviceDetails);

        auditRepository.save(auditEntity);
    }

    @Override
    public List<AuditEntity> getAuditsForUser(Long userId) {
        return List.of();
    }

    private String getClientIpAddress(HttpServletRequest request){
        String ip = "Unknown";
        try{
            ip = request.getRemoteAddr();
            if(ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
                InetAddress hostAddress = InetAddress.getLocalHost();
                ip = hostAddress.getHostAddress();
            }
        } catch (UnknownHostException  e) {
//            log.info("got unknown host");
            ip = "unknown";
        }
        return ip;
    }
//    private String getClientIpAddress(HttpServletRequest request) {
//        String ipAddress =  request.getHeader("X-FORWARDED-FOR");
//
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("Proxy-Client-IP");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("HTTP_X_FORWARDED");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("HTTP_FORWARDED_FOR");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("HTTP_FORWARDED");
//        }
//        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getRemoteAddr();
//        }
//        return ipAddress;
//    }
}
