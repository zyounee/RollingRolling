package com.example.hanghaeworld.service;

import com.example.hanghaeworld.dto.PostRequestDto;
import com.example.hanghaeworld.dto.PostResponseDto;
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
    public List<PostResponseDto> getPostList(Long userId) {
        List<Post> postList = postRepository.findAllByUserId(userId);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            postResponseDtoList.add(new PostResponseDto(post));
        }
        return postResponseDtoList;
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
}
