package com.example.hanghaeworld.repository;

import com.example.hanghaeworld.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByMasterId(Long userId);
}
