package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.Likes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesResponseDto {
    private boolean likes;

    public LikesResponseDto(Likes likes){
        this.likes = likes.isLike();
    }
}
