package com.example.hanghaeworld.repository;

import com.example.hanghaeworld.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMaster_IdAndCommentNullOrderByCreatedAtAsc(Long id);
    List<Post> findByMaster_IdAndCommentNotNull(Long id, Pageable pageable);

    List<Post> findByMaster_IdAndVisitor_IdOrderByCreatedAtDesc(Long masterId, Long visitorId);
    List<Post> findByMaster_IdAndVisitor_IdNot(Long masterId, Long visitorId, Pageable pageable);

    List<Post> findAllByMasterId(Long id);
}
