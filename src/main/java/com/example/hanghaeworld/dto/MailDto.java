package com.example.hanghaeworld.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.Getter;

import javax.validation.Valid;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private String email;
    private String name;
    private String message = "회원가입이 완료되었습니다.";

    public MailDto(@Valid SignupRequestDto user){
        this.email = user.getEmail();
        this.name = user.getUsername();
    }
}

