package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.Post;
import com.example.hanghaeworld.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyPostDto {
    private UserResponseDto user;
    private List<PostResponseDto> newPostList;
    private Page<PostResponseDto> postList;

    public MyPostDto(User user, List<PostResponseDto> newPostList, Page<PostResponseDto> page) {
        this.user = new UserResponseDto(user);
        this.newPostList = newPostList;
        this.postList = page;
    }
}
