package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class PostResponseDto implements Comparable<PostResponseDto> {
    private Long postId;
    private String nickName;
    private String content;
    private String image;
    private String createdAt;
    private CommentResponseDto commentResponseDto;
    private int postlike;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.nickName = post.isAnonymous() ? "익명" : post.getVisitor().getNickname();
        this.content = post.getContent();
        this.image = post.getImage();
        this.createdAt = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.commentResponseDto = post.getComment() == null ? null : new CommentResponseDto(post.getComment());
        this.postlike = post.getPostLikes().size();
    }

    @Override
    public int compareTo(PostResponseDto o) {
        return o.createdAt.compareTo(this.createdAt);
    }
}