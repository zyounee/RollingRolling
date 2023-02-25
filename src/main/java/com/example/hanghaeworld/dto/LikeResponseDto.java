package com.example.hanghaeworld.dto;


import com.example.hanghaeworld.entity.PostLike;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeResponseDto {
    private Long id;
    private boolean postLike;

    public LikeResponseDto(PostLike postLike){
        this.id =postLike.getId();
        this.postLike = postLike.isPostlike();
    }

}
