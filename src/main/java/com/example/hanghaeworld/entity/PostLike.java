package com.example.hanghaeworld.entity;

import com.example.hanghaeworld.dto.LikeRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "postlike")

@NoArgsConstructor
@Getter
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean postlike;

    @ManyToOne
    @JoinColumn
    private Post post;

    @ManyToOne
    @JoinColumn
    private User user;


    public PostLike(LikeRequestDto likeRequestDto, Post post, User user){
        this.post = post;
        this.postlike = likeRequestDto.isLike();
        this.user = user;
    }
}
