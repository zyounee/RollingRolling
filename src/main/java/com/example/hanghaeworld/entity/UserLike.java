package com.example.hanghaeworld.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //좋아요를 받는 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liked_user_id")
    private User likedUser;

    //좋아요를 누르는 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "likes_user_id")
    private User likesUser;

    public UserLike(User likedUser, User likesUser) {
        this.likedUser = likedUser;
        this.likesUser = likesUser;
    }
}
