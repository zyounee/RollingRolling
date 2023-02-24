package com.example.hanghaeworld.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private String content;
    private Boolean anonymous;
}
