package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.User;
import com.example.hanghaeworld.entity.UserLike;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String username;
    private String nickname;
    private String image;
    private String introduction;
    private String email;
    private boolean liked;
    private int likeCnt;

    public UserResponseDto(User user){
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.image = user.getImage();
        this.introduction = user.getIntroduction();
        this.email = user.getEmail();
        for (UserLike userLike : user.getLikes()) {
            if (userLike.getLikesUser().getUsername().equals(user.getUsername())) {
                this.liked = true;
                break;
            }
        }
        this.likeCnt = user.getLikeCnt();
    }
}
