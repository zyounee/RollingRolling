package com.example.hanghaeworld.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    @NotNull
    private String content;
    private Boolean anonymous;
    private String image;
}
