package com.example.hanghaeworld.controller;

import com.example.hanghaeworld.dto.LoginRequestDto;
import com.example.hanghaeworld.dto.SignupRequestDto;
import com.example.hanghaeworld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        userService.login(loginRequestDto, response);
    }


}
