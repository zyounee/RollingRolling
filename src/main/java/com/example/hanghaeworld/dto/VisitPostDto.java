package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class VisitPostDto {
    private UserResponseDto user;
    private List<PostResponseDto> myPostList;
    private Page<PostResponseDto> postList;

    public VisitPostDto(User user, List<PostResponseDto> myPostList, Page<PostResponseDto> page) {
        this.user = new UserResponseDto(user);
        this.myPostList = myPostList;
        this.postList = page;
    }
}
