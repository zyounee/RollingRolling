package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.Comment;
import com.example.hanghaeworld.entity.CommentLike;
import com.example.hanghaeworld.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private String nickname;
    private String createdAt;
    private boolean liked;
    private int likeCount;

    public CommentResponseDto(Comment comment, User user){
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.nickname = comment.getUser().getNickname();
        this.createdAt = comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        for (CommentLike commentLike : comment.getCommentLikes()){
            if (commentLike.getUser().equals(user)) {
                this.liked = true;
                break;
            }
        }
        this.likeCount = comment.getCommentLikes().size();
    }
}
