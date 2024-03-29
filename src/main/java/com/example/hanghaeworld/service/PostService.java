package com.example.hanghaeworld.service;

import com.example.hanghaeworld.dto.*;
import com.example.hanghaeworld.entity.*;
import com.example.hanghaeworld.repository.*;
import com.example.hanghaeworld.security.UserDetailsImpl;
import com.example.hanghaeworld.entity.Post;
import com.example.hanghaeworld.entity.User;
import com.example.hanghaeworld.repository.PostRepository;
import com.example.hanghaeworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;


    @Transactional(readOnly = true)
    public BoardDto getMyPost(String username, String masterName) {
        User master = getUser(masterName);
        List<Post> newPosts = postRepository.findByMaster_UsernameAndCommentNullOrderByCreatedAtDesc(username);
        List<PostResponseDto> newPostList = postListToDto(newPosts, master);

        Page<PostResponseDto> page = getMyPage(username, 1, master);
        return new BoardDto(true, new UserResponseDto(master), newPostList, page);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getMyPage(String username, int pageNum, User master) {
        Pageable pageable = getPageable(pageNum);
        List<Post> posts = postRepository.findByMaster_UsernameAndCommentNotNull(username, pageable);
        List<PostResponseDto> postDtoList = postListToDto(posts, master);
        return new PageImpl<>(postDtoList, pageable, postDtoList.size());
    }

    @Transactional(readOnly = true)
    public BoardDto getVisitPost(String username, User visitor) {
        User master = getUser(username);
        List<Post> myPosts = postRepository.findByMaster_UsernameAndVisitor_IdOrderByCreatedAtDesc(username, visitor.getId());
        List<PostResponseDto> myPostList = postListToDto(myPosts, visitor);

        Page<PostResponseDto> page = getVisitPage(username, 1, visitor);
        return new BoardDto(false, new UserResponseDto(master), myPostList, page);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getVisitPage(String username, int pageNum, User visitor) {
        Pageable pageable = getPageable(pageNum);
        List<Post> posts = postRepository.findByMaster_UsernameAndVisitor_IdNot(username, visitor.getId(), pageable);
        List<PostResponseDto> postDtoList = postListToDto(posts, visitor);
        return new PageImpl<>(postDtoList, pageable, postDtoList.size());
    }

    @Transactional
    public PostResponseDto writePost(String username, PostRequestDto postRequestDto, User user) {
        User master = getUser(username);
        Post post = new Post(master, postRequestDto, user);
        postRepository.save(post);
        return new PostResponseDto(post, user);
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, User user) {
        Post post = getPost(postId);
        if (!confirmVisitor(post, user)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        post.update(postRequestDto);
        return new PostResponseDto(post, user);
    }

    @Transactional
    public void deletePost(Long postId, User user) {
        Post post = getPost(postId);
        if (!confirmMaster(post, user) && !confirmVisitor(post, user)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        postRepository.delete(post);
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("없는 회원입니다."));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("없는 게시글입니다."));
    }

    private Pageable getPageable(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        // front 에서 page 구현이 아직 안되어서 999로 변경
        return PageRequest.of(page - 1, 999, sort);
    }

    private List<PostResponseDto> postListToDto(List<Post> newPosts, User user) {
        List<PostResponseDto> newPostList = new ArrayList<>();
        for (Post post : newPosts) {
            newPostList.add(new PostResponseDto(post, user));
        }
        return newPostList;
    }

    private boolean confirmVisitor(Post post, User user) {
        return post.getComment() == null && post.getVisitor().getUsername().equals(user.getUsername());
    }

    private boolean confirmMaster(Post post, User user) {
        return post.getMaster().getUsername().equals(user.getUsername());
    }

    @Transactional
    public LikeResponseDto like(Long postId, LikeRequestDto likeRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.")
        );

        if (likeRequestDto.isLiked()) {
            PostLike postlikes = postLikeRepository.findByPostIdAndUserId(post.getId(), userDetails.getUser().getId()).orElseThrow(
                    () -> new IllegalArgumentException("좋아요 정보가 잘못되었습니다.")
            );
            postLikeRepository.delete(postlikes);
        } else {
            postLikeRepository.saveAndFlush(new PostLike(post, userDetails.getUser()));
        }

        return new LikeResponseDto(!likeRequestDto.isLiked(), post.getPostLikes().size());
    }

}
