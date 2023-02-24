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
public class Post extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String nickname;
    @OneToOne(mappedBy = "post")
    private Comment comment;

    public Post(User master, PostRequestDto postRequestDto, User user) {
        this.user = master;
        this.content = postRequestDto.getContent();
        this.nickname = postRequestDto.getAnonymous() ? "익명" : user.getNickname();
    }
}
