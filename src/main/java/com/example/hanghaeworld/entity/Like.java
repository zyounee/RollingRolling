package com.example.hanghaeworld.entity;

import com.example.hanghaeworld.dto.LikeRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "liked")

@NoArgsConstructor
@Getter
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean like;

    @ManyToOne
    @JoinColumn
    private Post post;

    @ManyToOne
    @JoinColumn
    private User user;


    public Like(LikeRequestDto likeRequestDto, Post post, User user){
        this.post = post;
        this.like = likeRequestDto.isLike();
        this.user = user;
    }
}
