package com.example.hanghaeworld.service;

import com.example.hanghaeworld.dto.SignupRequestDto;
import com.example.hanghaeworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void signup(@Valid SignupRequestDto signupRequestDto){
        String username = signupRequestDto.
    }

}
