package com.example.hanghaeworld.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLikeResponseDto {
    private boolean isLike;

    public UserLikeResponseDto(boolean isLike) {
        this.isLike = isLike;
    }
}
