package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String nickName;
    private String content;
    private LocalDateTime createdAt;
    private CommentResponseDto commentResponseDto;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.nickName = post.isAnonymous() ? "익명" : post.getVisitor().getNickname();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.commentResponseDto = new CommentResponseDto(post.getComment());
    }
}
