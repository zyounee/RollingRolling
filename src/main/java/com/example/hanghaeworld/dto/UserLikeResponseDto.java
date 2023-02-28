package com.example.hanghaeworld.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLikeResponseDto {
    private Boolean isLike;

    public UserLikeResponseDto(Boolean isLike) {
        this.isLike = isLike;
    }
}
