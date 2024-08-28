package com.example.userregistrationandlogin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_token_table")
public class UserTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="token_id")
    private Long id;

    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;


    @OneToOne(targetEntity = UserEntity.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private UserEntity user;

    public boolean isValid(){
        return new Date().before(this.expirationDate);
    }

}
