package com.example.userregistrationandlogin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="user-uploads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FileStorageEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;

    private String name;

    private String type;

    @Lob
    private byte[] data;

    public FileStorageEntity(String name, String type, byte[] data){
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
