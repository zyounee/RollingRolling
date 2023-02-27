package com.example.hanghaeworld.controller;

import com.example.hanghaeworld.service.S3UploadService;
import com.example.hanghaeworld.dto.*;
import com.example.hanghaeworld.security.UserDetailsImpl;
import com.example.hanghaeworld.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final S3UploadService s3UploadService;

    @GetMapping("/post/{username}")
    public BoardDto getPost(@PathVariable String username,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails.getUser().getUsername().equals(username)) {
            return postService.getMyPost(username);
        }
        return postService.getVisitPost(username, userDetails.getUser());
    }

    @GetMapping("/post")
    public Page<PostResponseDto> getPostPage(@RequestParam("pageNum") int pageNum,
                                             @RequestParam("username") String username,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails.getUser().getUsername().equals(username)) {
            return postService.getMyPage(username, pageNum);
        }
        return postService.getVisitPage(username, pageNum, userDetails.getUser());
    }

    @PostMapping("/post/{username}")
    public PostResponseDto writePost(@PathVariable String username,
                                     @Valid @RequestBody PostRequestDto postRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.writePost(username, postRequestDto, userDetails.getUser());
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

    //todo 여기에 /api 있던거 수정했습니다. 그런데 UserController로 옮겨야 하지 않나 싶습니다.
    @PutMapping("/profile/{userId}")
    public UserResponseDto updateProfile(@PathVariable Long userId,
                                         @RequestBody UserRequestDto userRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updateProfile(userId, userRequestDto, userDetails.getUser());
    }

    //todo 뭔가 void로 해도 될것 같아요. 프론트에서는 클릭하면 무조건 하트 색깔 전환 되고, 데이터는 저희가 알아서 처리하는 방식? 한번 논의를 해봐요
    @PostMapping("/post/like/{postId}")
    public void like( @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.like(postId, userDetails);
    }

    @PostMapping("/upload")
    public String uploadImage (@RequestPart(value = "img") MultipartFile multipartFile) throws IOException {
        return s3UploadService.uploadFiles(multipartFile, "rollingrollingbucket");
    }
}
