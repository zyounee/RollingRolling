package com.example.hanghaeworld.controller;

import com.example.hanghaeworld.dto.*;
import com.example.hanghaeworld.security.UserDetailsImpl;
import com.example.hanghaeworld.service.MailService;
import com.example.hanghaeworld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final MailService mailService;


    @PostMapping("/user/signup")
    public ModelAndView signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
//        mailService.sendMail(new MailDto(signupRequestDto));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/user/info")
    public UserResponseDto getInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getInfo(userDetails.getUsername());
    }

    @PostMapping("/user/login")
    public UserResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.login(loginRequestDto, response);
    }

    //전체 회원 조회
    @GetMapping("/home/{page}")
    public List<UserResponseDto> getUsers(@PathVariable int page){
        return userService.getUsers(page);
    }


    //특정 회원 검색 조회
    @GetMapping("/user/search")
    public UserSearchResponseDto search(@RequestParam String nickname){
        return userService.search(nickname);
    }
    @PostMapping("/user/checkpwd")
    public boolean checkPassword(@RequestBody PasswordRequestDto passwordRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.checkPassword(passwordRequestDto, userDetails.getUser());
    }
    //내 정보 변경
    @PutMapping("/mypage")
    public UserResponseDto updateProfile(@RequestBody UserRequestDto userRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.updateProfile(userRequestDto, userDetails.getUser());
    }

    @PostMapping("user/like/{likedUsername}")
    public LikeResponseDto likeUser(@PathVariable String likedUsername,
                                        @RequestBody LikeRequestDto likeRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.likeUser(likedUsername, likeRequestDto, userDetails);
    }

}
