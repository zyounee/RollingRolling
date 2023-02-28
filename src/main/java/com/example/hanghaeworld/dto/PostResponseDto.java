package com.example.hanghaeworld.dto;

import com.example.hanghaeworld.entity.Post;
import com.example.hanghaeworld.entity.PostLike;
import com.example.hanghaeworld.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class PostResponseDto implements Comparable<PostResponseDto> {
    private Long postId;
    private String nickname;
    private String content;
    private String image;
    private String createdAt;
    private CommentResponseDto commentResponseDto;
    private boolean liked;
    private int likeCount;

    public PostResponseDto(Post post, User user) {
        this.postId = post.getId();
        this.nickname = post.isAnonymous() ? "익명" : post.getVisitor().getNickname();
        this.content = post.getContent();
        this.image = post.getImage();
        this.createdAt = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.commentResponseDto = post.getComment() == null ? null : new CommentResponseDto(post.getComment(),user);
        for (PostLike postLike : post.getPostLikes()) {
            if (postLike.getUser().equals(user)) {
                this.liked = true;
                break;
            }
        }
        this.likeCount = post.getPostLikes().size();
    }

    @Override
    public int compareTo(PostResponseDto o) {
        return o.createdAt.compareTo(this.createdAt);
    }
}