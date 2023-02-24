package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyPostDto {
    private UserResponseDto user;
    private List<PostResponseDto> newPostList;
    private List<PostResponseDto> postList;

    public MyPostDto(User user, List<PostResponseDto> newPostList, List<PostResponseDto> postList) {
        this.user = new UserResponseDto(user);
        this.newPostList = newPostList;
        this.postList = postList;
    }
}
