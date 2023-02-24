package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class VisitPostDto {
    private UserResponseDto user;
    private List<PostResponseDto> myPostList;
    private List<PostResponseDto> postList;

    public VisitPostDto(User user, List<PostResponseDto> myPostList, List<PostResponseDto> postList) {
        this.user = new UserResponseDto(user);
        this.myPostList = myPostList;
        this.postList = postList;
    }
}
