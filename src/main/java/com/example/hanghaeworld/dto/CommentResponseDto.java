package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private int commentlike;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.nickname = comment.getUser().getNickname();
        this.createdAt = comment.getCreatedAt();
        this.commentlike = comment.getCommentLikes().size();
    }
}
