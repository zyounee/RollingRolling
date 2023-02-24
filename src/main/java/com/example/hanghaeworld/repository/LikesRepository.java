package com.example.hanghaeworld.repository;

import com.example.hanghaeworld.entity.Like;
import com.example.hanghaeworld.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByComment_IdAndUser_Id(Long id, Long id1);

}
