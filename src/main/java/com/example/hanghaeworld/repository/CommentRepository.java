package com.example.hanghaeworld.repository;

import com.example.hanghaeworld.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
