package com.example.hanghaeworld.entity;

import com.example.hanghaeworld.dto.LikeRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@Getter
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean commentlike;

    @ManyToOne
    @JoinColumn
    private Comment comment;

    @ManyToOne
    @JoinColumn
    private User user;

    public CommentLike(LikeRequestDto likeRequestDto, Comment comment, User user){
        this.comment = comment;
        this.commentlike = likeRequestDto.isLike();
        this.user = user;
    }
}

