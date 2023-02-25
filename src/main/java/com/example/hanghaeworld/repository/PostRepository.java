package com.example.hanghaeworld.repository;

import com.example.hanghaeworld.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMaster_UsernameAndCommentNullOrderByCreatedAtAsc(String username);
    List<Post> findByMaster_UsernameAndCommentNotNull(String username, Pageable pageable);

    List<Post> findByMaster_UsernameAndVisitor_IdOrderByCreatedAtDesc(String username, Long visitorId);
    List<Post> findByMaster_UsernameAndVisitor_IdNot(String username, Long visitorId, Pageable pageable);

    List<Post> findAllByMasterId(Long id);
}
