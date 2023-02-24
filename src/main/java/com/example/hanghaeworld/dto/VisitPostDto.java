package com.example.hanghaeworld.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class VisitPostDto {
    private List<PostResponseDto> myPostList;
    private List<PostResponseDto> postList;

    public VisitPostDto(List<PostResponseDto> myPostList, List<PostResponseDto> postList) {
        this.myPostList = myPostList;
        this.postList = postList;
    }
}
