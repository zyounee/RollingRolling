package com.example.hanghaeworld.service;

import com.example.hanghaeworld.dto.*;
import com.example.hanghaeworld.entity.*;
import com.example.hanghaeworld.repository.*;
import com.example.hanghaeworld.security.UserDetailsImpl;
import com.example.hanghaeworld.dto.*;
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
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final LikesRepository likesRepository;

    @Transactional(readOnly = true)
    public MyPostDto getMyPost(String username, User user) {
        List<Post> newPosts = postRepository.findByMaster_UsernameAndCommentNullOrderByCreatedAtAsc(username);
        List<PostResponseDto> newPostList = new ArrayList<>();
        for (Post post : newPosts) {
            newPostList.add(new PostResponseDto(post));
        }

        Pageable pageable = getPageable(1);
        List<Post> postList = postRepository.findByMaster_UsernameAndCommentNotNull(username, pageable);
        Page<PostResponseDto> page = getPage(pageable, postList);
        return new MyPostDto(user, newPostList, page);
    }

    @Transactional(readOnly = true)
    public VisitPostDto getVisitPost(String username, User user) {
        List<Post> myPosts = postRepository.findByMaster_UsernameAndVisitor_IdOrderByCreatedAtDesc(username, user.getId());
        List<PostResponseDto> myPostList = new ArrayList<>();
        for (Post post : myPosts) {
            myPostList.add(new PostResponseDto(post));
        }

        Pageable pageable = getPageable(1);
        List<Post> posts = postRepository.findByMaster_UsernameAndVisitor_IdNot(username, user.getId(), pageable);
        Page<PostResponseDto> page = getPage(pageable, posts);

        return new VisitPostDto(user, myPostList, page);
    }

    @Transactional
    public PostResponseDto writePost(String username, PostRequestDto postRequestDto, User user) {
        User master = getUser(username);
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

    private User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("없는 회원입니다."));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("없는 게시글입니다."));
    }

    private Pageable getPageable(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, 3, sort);
        return pageable;
    }

    private Page<PostResponseDto> getPage(Pageable pageable, List<Post> posts) {
        List<PostResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            postList.add(new PostResponseDto(post));
        }
        return new PageImpl<>(postList, pageable, postList.size());
    }

    private void confirm(Post post, User user) {
        if (post.getMaster().getUsername().equals(user.getUsername()) || (post.getComment() == null && post.getVisitor().equals(user.getUsername()))) {
            return;
        }

        throw new IllegalArgumentException("권한이 없습니다.");
    }

    @Transactional
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

    @Transactional
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

    public UserResponseDto updateProfile(Long masterId, UserRequestDto userRequestDto, User visitor) {
        User master = userRepository.findById(masterId).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        if (!master.getUsername().equals(visitor.getUsername()))
        {throw new IllegalArgumentException("권한이 없습니다.");}
        master.update(userRequestDto);
        return new UserResponseDto(master);
    }
}
