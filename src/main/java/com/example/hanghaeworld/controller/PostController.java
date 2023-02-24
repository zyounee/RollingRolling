package com.example.hanghaeworld.controller;

import com.example.hanghaeworld.dto.MyPostDto;
import com.example.hanghaeworld.dto.PostRequestDto;
import com.example.hanghaeworld.dto.PostResponseDto;
import com.example.hanghaeworld.dto.VisitPostDto;
import com.example.hanghaeworld.security.UserDetailsImpl;
import com.example.hanghaeworld.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @GetMapping("/post/mypost/{userId}")
    public MyPostDto getMyPost(@PathVariable Long userId) {
        return postService.getMyPost(userId);
    }

    @GetMapping("/post/visitpost/{userId}")
    public VisitPostDto getVisitPost(@PathVariable Long userId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getVisitPost(userId, userDetails.getUser());
    }

    @PostMapping("/post/{userId}")
    public PostResponseDto writePost(@PathVariable Long userId,
                                     @Valid @RequestBody PostRequestDto postRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.writePost(userId, postRequestDto, userDetails.getUser());
    }

    @PutMapping("/post/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId,
                                      @Valid @RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(postId, postRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/post/{postId}")
    public void deletePost(@PathVariable Long postId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());
    }

}
