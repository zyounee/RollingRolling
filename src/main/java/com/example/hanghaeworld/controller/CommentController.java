package com.example.hanghaeworld.controller;

import com.example.hanghaeworld.dto.CommentRequestDto;
import com.example.hanghaeworld.dto.CommentResponseDto;

import com.example.hanghaeworld.dto.LikeRequestDto;
import com.example.hanghaeworld.dto.LikeResponseDto;
import com.example.hanghaeworld.security.UserDetailsImpl;
import com.example.hanghaeworld.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{postid}")
    public CommentResponseDto commentWrite(@PathVariable Long postid, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.commentWrite(postid, commentRequestDto, userDetails);
    }

    @PostMapping("/comment/like/{commentId}")
    public LikeResponseDto likes(@PathVariable Long commentId,
                                 @RequestBody LikeRequestDto likeRequestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.likes(commentId, likeRequestDto, userDetails);
    }
}
