package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserSearchResponseDto {
    private UserResponseDto userResponseDto;
    private int newPostCnt;
    private int comPostCnt;

    public UserSearchResponseDto(User user, int newPostCnt, int comPostCnt) {
        this.userResponseDto = new UserResponseDto(user);
        this.newPostCnt = newPostCnt;
        this.comPostCnt = comPostCnt;
    }
}
