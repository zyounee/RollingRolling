package com.example.hanghaeworld.service;


import com.example.hanghaeworld.dto.CommentRequestDto;
import com.example.hanghaeworld.dto.CommentResponseDto;
import com.example.hanghaeworld.entity.Comment;
import com.example.hanghaeworld.entity.Post;
import com.example.hanghaeworld.repository.CommentRepository;
import com.example.hanghaeworld.repository.PostRepository;
import com.example.hanghaeworld.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    public CommentResponseDto commentWrite(Long postid, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new IllegalArgumentException("질문이 없습니다")
        );
        if (Objects.equals(post.getMaster().getUsername(), userDetails.getUsername())){
            Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto,post,userDetails.getUser()));
            return new CommentResponseDto(comment);
        }else{
            throw new IllegalArgumentException("마스터가 아닙니다");
        }
    }
}
