package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String nickname;
    private String image;
    private String introduction;

    public UserResponseDto(User user){
        this.nickname = user.getNickname();
        this.image = user.getImage();
        this.introduction = user.getIntroduction();
    }
}
