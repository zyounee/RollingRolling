package com.example.hanghaeworld.service;


import com.example.hanghaeworld.dto.CommentRequestDto;
import com.example.hanghaeworld.dto.CommentResponseDto;

import com.example.hanghaeworld.entity.Comment;
import com.example.hanghaeworld.entity.CommentLike;
import com.example.hanghaeworld.entity.Post;
import com.example.hanghaeworld.repository.CommentRepository;
import com.example.hanghaeworld.repository.CommentLikeRepository;
import com.example.hanghaeworld.repository.PostRepository;
import com.example.hanghaeworld.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public CommentResponseDto commentWrite(Long postid, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new IllegalArgumentException("질문이 없습니다")
        );
        if (Objects.equals(post.getMaster().getUsername(), userDetails.getUsername())){
            Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto,post,userDetails.getUser()));
            return new CommentResponseDto(comment,userDetails.getUser());
        }else{
            throw new IllegalArgumentException("마스터가 아닙니다");
        }
    }

    @Transactional
    public void likes( Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.")
        );
        Optional<CommentLike> commentlikes =  commentLikeRepository.findByComment_IdAndUser_Id(comment.getId(), userDetails.getUser().getId());
        if (commentlikes.isPresent()) {
            commentLikeRepository.delete(commentlikes.get());
        } else {
            commentLikeRepository.saveAndFlush(new CommentLike(comment, userDetails.getUser()));

        }
    }

}
