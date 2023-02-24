package com.example.hanghaeworld.controller;


import com.example.hanghaeworld.dto.CommentRequestDto;
import com.example.hanghaeworld.dto.PostRequestDto;
import com.example.hanghaeworld.dto.PostResponseDto;
import com.example.hanghaeworld.security.UserDetailsImpl;
import com.example.hanghaeworld.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @GetMapping("/post/{userId}")
    public List<PostResponseDto> getPostList(@PathVariable Long userId){
        return postService.getPostList(userId);
    }

    @PostMapping("/post/{userId}")
    public PostResponseDto writePost(@PathVariable Long userId,
                                     @RequestBody PostRequestDto postRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.writePost(userId, postRequestDto, userDetails.getUser());
    }

}
