package com.example.userregistrationandlogin.repository;

import com.example.userregistrationandlogin.entity.FileStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStorageRepository extends JpaRepository<FileStorageEntity, String> {
}
