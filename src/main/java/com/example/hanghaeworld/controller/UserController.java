package com.example.hanghaeworld.controller;

import com.example.hanghaeworld.dto.LoginRequestDto;
import com.example.hanghaeworld.dto.SignupRequestDto;
import com.example.hanghaeworld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public ModelAndView signup(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        userService.login(loginRequestDto, response);
    }


}
