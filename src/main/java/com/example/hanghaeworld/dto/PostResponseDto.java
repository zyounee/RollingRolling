package com.example.hanghaeworld.dto;

import java.time.LocalDateTime;

public class PostResponseDto {
    private Long postId;
    private String nickName;
    private String content;
    private LocalDateTime createdAt;
    private CommentResponseDto commentResponseDto;
}
