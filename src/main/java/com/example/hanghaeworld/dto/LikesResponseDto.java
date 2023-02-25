package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.CommentLike;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesResponseDto {
    private Long id;
    private boolean commentlike;

    public LikesResponseDto(CommentLike commentLike){
        this.id = commentLike.getId();
        this.commentlike = commentLike.isCommentlike();
    }
}
