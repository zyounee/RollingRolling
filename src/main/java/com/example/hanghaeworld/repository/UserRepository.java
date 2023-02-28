package com.example.hanghaeworld.repository;

import com.example.hanghaeworld.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByGoogleId(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAllByOrderByNicknameAsc(Pageable pageable);
}
