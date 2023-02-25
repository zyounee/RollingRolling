package com.example.hanghaeworld.service;

import com.example.hanghaeworld.dto.*;
import com.example.hanghaeworld.entity.*;
import com.example.hanghaeworld.repository.*;
import com.example.hanghaeworld.security.UserDetailsImpl;
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
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final LikesRepository likesRepository;

    @Transactional(readOnly = true)
    public MyPostDto getMyPost(Long userId, User user) {
        List<Post> posts = postRepository.findAllByMasterId(userId);

        List<PostResponseDto> newPostList = new ArrayList<>();
        List<PostResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            if (post.getComment() == null) {
                newPostList.add(new PostResponseDto(post));
                continue;
            }
            postList.add(new PostResponseDto(post));
        }
        return new MyPostDto(user, newPostList, postList);
    }

    @Transactional(readOnly = true)
    public VisitPostDto getVisitPost(Long userId, User user) {
        List<Post> posts = postRepository.findAllByMasterId(userId);

        List<PostResponseDto> myPostList = new ArrayList<>();
        List<PostResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            if (post.getVisitor().getUsername().equals(user.getUsername())) {
                myPostList.add(new PostResponseDto(post));
                continue;
            }
            postList.add(new PostResponseDto(post));
        }
        return new VisitPostDto(user, myPostList, postList);
    }

    @Transactional
    public PostResponseDto writePost(Long userId, PostRequestDto postRequestDto, User user) {
        User master = getUser(userId);
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

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("없는 회원입니다.")
        );
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



    public LikeResponseDto like(LikeRequestDto likeRequestDto, Long postId, UserDetailsImpl userDetails) {
       Post post =  postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.")
        );
        PostLike postLike = (new PostLike(likeRequestDto, post, userDetails.getUser()));
        if (likeRequestDto.isLike() == true) {
            likeRepository.saveAndFlush(new PostLike(likeRequestDto, post, userDetails.getUser()));
            if (likeRepository.findByPost_IdAndUser_Id(userDetails.getUser().getId(), post.getId()).isPresent()) {
            }
        } else {
            likeRepository.deleteById(userDetails.getUser().getId());
            return null;
        }
        return new LikeResponseDto(postLike);
    }

    public LikesResponseDto likes(LikeRequestDto likeRequestDto, Long commentId, UserDetailsImpl userDetails) {
        Comment comment =  commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.")
        );
        CommentLike commentLike = (new CommentLike(likeRequestDto, comment, userDetails.getUser()));
        if (likeRequestDto.isLike() == true)
        {
            likesRepository.saveAndFlush(new CommentLike(likeRequestDto, comment, userDetails.getUser()));
            if (likesRepository.findByComment_IdAndUser_Id(userDetails.getUser().getId(), comment.getId()).isPresent()) {
            }
        } else {
            likesRepository.deleteById(userDetails.getUser().getId());
            return null;
        }
        return new LikesResponseDto(commentLike);
    }
}
