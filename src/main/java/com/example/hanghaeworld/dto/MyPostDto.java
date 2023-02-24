package com.example.hanghaeworld.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyPostDto {
    private List<PostResponseDto> newPostList;
    private List<PostResponseDto> postList;

    public MyPostDto(List<PostResponseDto> newPostList, List<PostResponseDto> postList) {
        this.newPostList = newPostList;
        this.postList = postList;
    }
}
