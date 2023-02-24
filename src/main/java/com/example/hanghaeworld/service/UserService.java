package com.example.hanghaeworld.service;

import com.example.hanghaeworld.dto.LoginRequestDto;
import com.example.hanghaeworld.dto.SignupRequestDto;
import com.example.hanghaeworld.dto.UserSearchRequestDto;
import com.example.hanghaeworld.dto.UserSearchResponseDto;
import com.example.hanghaeworld.entity.Post;
import com.example.hanghaeworld.entity.User;
import com.example.hanghaeworld.entity.UserRoleEnum;
import com.example.hanghaeworld.jwt.JwtUtil;
import com.example.hanghaeworld.repository.PostRepository;
import com.example.hanghaeworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(@Valid SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String nickname = signupRequestDto.getNickname();
        String email = signupRequestDto.getEmail();

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자 존재");
        }
        UserRoleEnum role = UserRoleEnum.USER;

        User user = new User(username, password, nickname, email, role);
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

    @Transactional
    public UserSearchResponseDto search(UserSearchRequestDto userSearchRequestDto){
        User user = null;
        // searchType이 username일 때
        if (userSearchRequestDto.getSearchType().equals("username")){
            user = userRepository.findByUsername(userSearchRequestDto.getInput()).orElseThrow(
                    () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
            );
            // searchType이 nickname일 때
        } else if (userSearchRequestDto.getSearchType().equals("nickname")) {
            user = userRepository.findByNickname(userSearchRequestDto.getInput()).orElseThrow(
                    () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
            );
        }

        // 이 유저의 id를 ? master_id로 가진 post id를 알아내

        List<Post> posts = postRepository.findAllByMasterId(user.getId());
        int newPostCnt = 0;
        int comPostCnt = posts.size();
        // 반복문을 돌면서 post:posts에 comment가 없으면 new값을 +1
        // 반복문이 끝나면 comansCnt = posts.size()-new

        for (Post post: posts){
            if (post.getComment() == null){
                newPostCnt++;
            }
        }
        comPostCnt -= newPostCnt;

        return new UserSearchResponseDto(user, newPostCnt, comPostCnt);

    }

}
