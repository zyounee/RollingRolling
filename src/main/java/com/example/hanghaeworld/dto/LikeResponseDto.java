package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.Like;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeResponseDto {
    private boolean like;

    public LikeResponseDto(Like like){
        this.like = like.isLike();
    }

}
