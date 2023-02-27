package com.example.hanghaeworld.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    private String image;
    private String introduction;
    @NotNull
    private String currentPassword;
    @Pattern(regexp = "(?=.*?[a-zA-Z])(?=.*?[\\d])(?=.*?[~!@#$%^&*()_+=\\-`]).{8,15}")
    private String newPassword;
    private String newPasswordConfirm;
    @NotNull
    private String nickname;
}
