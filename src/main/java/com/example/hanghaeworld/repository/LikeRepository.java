package com.example.hanghaeworld.repository;

import com.example.hanghaeworld.entity.Like;
import com.example.hanghaeworld.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPost_IdAndUser_Id(Long id, Long id1);

}
