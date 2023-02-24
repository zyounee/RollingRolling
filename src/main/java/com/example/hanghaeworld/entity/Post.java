package com.example.hanghaeworld.entity;

import com.example.hanghaeworld.dto.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean anonymous;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", nullable = false)
    private User master;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id", nullable = false)
    private User visitor;

    @OneToOne(mappedBy = "post")
    private Comment comment;

    public Post(User master, PostRequestDto postRequestDto, User user) {
        this.master = master;
        this.content = postRequestDto.getContent();
        this.image = postRequestDto.getImage();
        this.visitor = user;
        this.anonymous = postRequestDto.getAnonymous();
    }

    public void update(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
    }
}
