package com.example.hanghaeworld.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z\\d`]{4,10}$")
    private String username;
    @Pattern(regexp = "^[a-zA-Z\\d`~!@#$%^&*()-_=+]{8,15}$")
    private String password;
    private String email;
    private String nickname;
}
