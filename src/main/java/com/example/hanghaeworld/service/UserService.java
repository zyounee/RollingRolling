package com.example.hanghaeworld.service;

import com.example.hanghaeworld.dto.*;
import com.example.hanghaeworld.entity.Post;
import com.example.hanghaeworld.entity.User;
import com.example.hanghaeworld.entity.UserLike;
import com.example.hanghaeworld.entity.UserRoleEnum;
import com.example.hanghaeworld.jwt.JwtUtil;
import com.example.hanghaeworld.repository.PostRepository;
import com.example.hanghaeworld.repository.UserLikeRepository;
import com.example.hanghaeworld.repository.UserRepository;
import com.example.hanghaeworld.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
//import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserLikeRepository userLikeRepository;

    @Transactional
    public void signup(@Valid SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
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
    public UserResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다")
        );

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀 번호가 틀롤링");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new UserResponseDto(user);
    }

    //전체 회원 조회
    @Transactional
    public List<UserResponseDto> getUsers(int page){
        //Sort sort = Sort.by(Sort.Direction.ASC, "nickname");
        Pageable pageable = PageRequest.of(page - 1,30);
        List<User> users = userRepository.findAllByOrderByNicknameAsc(pageable);
        List<UserResponseDto> userResponseDtos = users.stream()
                .map(user-> new UserResponseDto(user))
                .collect(Collectors.toList());
        return userResponseDtos;
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

    public boolean likeUser(Long likedUserid, UserDetailsImpl userDetails) {
        User likedUser = userRepository.findById(likedUserid).orElseThrow(
                () -> new NullPointerException("해당 유저가 없습니다.")
        );
        User likesUser = userDetails.getUser();
        Optional<UserLike> optionalUserLike = userLikeRepository.findByLikedUserAndLikesUser(likedUser, likesUser);
        if (optionalUserLike.isPresent()){
            userLikeRepository.deleteById(optionalUserLike.get().getId());
            likedUser.setLikeCnt(likedUser.getLikeCnt()-1);
            userRepository.save(likedUser);
//            return new UserResponseDto(likedUser);
            return false;
        }
        userLikeRepository.save(new UserLike(likedUser, likesUser));
        likedUser.setLikeCnt(likedUser.getLikeCnt()+1);
        userRepository.save(likedUser);
//        return new UserResponseDto(likedUser);
        return true;
    }


    @Transactional
    public UserResponseDto updateProfile(UserRequestDto userRequestDto, User user) {
        User master = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        if (!userRequestDto.getNewPassword().isEmpty() && userRequestDto.getNewPassword().equals(userRequestDto.getNewPasswordConfirm())){
            master.updatePassword(passwordEncoder.encode(userRequestDto.getNewPassword()));
        }
        else if (!userRequestDto.getNewPassword().equals(userRequestDto.getNewPasswordConfirm())) {
            throw new IllegalArgumentException("변경하려는 비밀 번호가 틀롤링");
        }
        master.update(userRequestDto);
        return new UserResponseDto(master);
    }

    @Transactional
    public void checkPassword(PasswordRequestDto passwordRequestDto, User user) {
        User master = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        if (!passwordEncoder.matches(passwordRequestDto.getPassword(), master.getPassword())){
            throw new IllegalArgumentException("비밀 번호가 틀롤링");
        }
    }
}
