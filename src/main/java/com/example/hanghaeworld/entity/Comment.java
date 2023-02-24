package com.example.hanghaeworld.entity;

import com.example.hanghaeworld.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

//    @OneToOne
//    @JoinColumn(name = "post_id", nullable = false)
//    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(CommentRequestDto commentRequestDto,Post post){
        this.content = commentRequestDto.getContent();
        this.post = post;
    }
}
