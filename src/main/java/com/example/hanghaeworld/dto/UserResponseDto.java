package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String username;
    private String nickname;
    private String image;
    private String introduction;
    private int likeCnt;

    public UserResponseDto(User user){
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.image = user.getImage();
        this.introduction = user.getIntroduction();
        this.likeCnt = user.getLikeCnt();
    }
}
