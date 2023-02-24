package com.example.hanghaeworld.service;

import com.example.hanghaeworld.dto.MyPostDto;
import com.example.hanghaeworld.dto.PostRequestDto;
import com.example.hanghaeworld.dto.PostResponseDto;
import com.example.hanghaeworld.dto.VisitPostDto;
import com.example.hanghaeworld.entity.Post;
import com.example.hanghaeworld.entity.User;
import com.example.hanghaeworld.repository.PostRepository;
import com.example.hanghaeworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public MyPostDto getMyPost(Long userId, User user) {
        List<Post> posts = postRepository.findAllByMasterId(userId);

        List<PostResponseDto> newPostList = new ArrayList<>();
        List<PostResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            if (post.getComment() == null) {
                newPostList.add(new PostResponseDto(post));
            } else {
                postList.add(new PostResponseDto(post));
            }
        }
        return new MyPostDto(newPostList, postList);
    }

    @Transactional(readOnly = true)
    public VisitPostDto getVisitPost(Long userId, User user) {
        List<Post> posts = postRepository.findAllByMasterId(userId);

        List<PostResponseDto> myPostList = new ArrayList<>();
        List<PostResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            if (post.getVisitor().getUsername().equals(user.getUsername())) {
                myPostList.add(new PostResponseDto(post));
            } else {
                postList.add(new PostResponseDto(post));
            }
        }
        return new VisitPostDto(myPostList, postList);
    }

    @Transactional
    public PostResponseDto writePost(Long userId, PostRequestDto postRequestDto, User user) {
        User master = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("없는 회원입니다.")
        );
        Post post = new Post(master, postRequestDto, user);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, User user) {
        Post post = getPost(postId);
        confirm(post, user);
        post.update(postRequestDto);
        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long postId, User user) {
        Post post = getPost(postId);
        confirm(post, user);
        postRepository.delete(post);
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.")
        );
    }

    private void confirm(Post post, User user) {
        if (post.getMaster().getUsername().equals(user.getUsername()) ||
                (post.getComment() == null && post.getVisitor().equals(user.getUsername()))) {
            return;
        }
        throw new IllegalArgumentException("권한이 없습니다.");
    }
}
