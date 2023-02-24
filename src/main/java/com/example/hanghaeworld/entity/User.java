package com.example.hanghaeworld.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[a-z\\d`]{4,10}$")
    private String username;
    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\d`~!@#$%^&*()-_=+]{8,15}$")
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Column
    @Email
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, String nickName, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickName;
        this.email = email;
    }
}
