package com.example.hanghaeworld.repository;

import com.example.hanghaeworld.entity.User;
import com.example.hanghaeworld.entity.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    Optional<UserLike> findByLikedUserAndLikesUser(User likedUser, User likesUser);
}
