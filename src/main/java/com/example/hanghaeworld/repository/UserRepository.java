package com.example.hanghaeworld.repository;

import com.example.hanghaeworld.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
