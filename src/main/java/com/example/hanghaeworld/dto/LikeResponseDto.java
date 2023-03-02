package com.example.hanghaeworld.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class LikeResponseDto {
    private Boolean liked;
    private int likeCount;

    public LikeResponseDto(Boolean liked, int likeCount) {
        this.liked = liked;
        this.likeCount = likeCount;
    }
}
