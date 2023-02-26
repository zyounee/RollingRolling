package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardDto {
    private boolean myPost;
    private UserResponseDto user;
    private List<PostResponseDto> upperPost;
    private Page<PostResponseDto> bottomPost;

    public BoardDto(boolean myPost, User user, List<PostResponseDto> newPostList, Page<PostResponseDto> page) {
        this.myPost = myPost;
        this.user = new UserResponseDto(user);
        this.upperPost = newPostList;
        this.bottomPost = page;
    }
}
