package com.example.hanghaeworld.service;

import com.example.hanghaeworld.dto.LoginRequestDto;
import com.example.hanghaeworld.dto.SignupRequestDto;
import com.example.hanghaeworld.entity.User;
import com.example.hanghaeworld.entity.UserRoleEnum;
import com.example.hanghaeworld.jwt.JwtUtil;
import com.example.hanghaeworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(@Valid SignupRequestDto signupRequestDto){

        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String nickName = signupRequestDto.getNickName();
        String email = signupRequestDto.getEmail();


        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자 존재");
        }
        UserRoleEnum role = UserRoleEnum.USER;

        User user = new User(username, password, nickName, email, role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다")
        );

        if (!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀 번호가 틀롤링");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

    }

}
