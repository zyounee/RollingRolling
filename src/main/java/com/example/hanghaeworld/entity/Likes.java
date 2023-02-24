package com.example.hanghaeworld.entity;

import com.example.hanghaeworld.dto.CommentRequestDto;
import com.example.hanghaeworld.dto.LikeRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@Getter
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean like;

    @ManyToOne
    @JoinColumn
    private Comment comment;

    @ManyToOne
    @JoinColumn
    private User user;

    public Likes(LikeRequestDto likeRequestDto, Comment comment, User user){
        this.comment = comment;
        this.like = likeRequestDto.isLike();
        this.user = user;
    }
}

