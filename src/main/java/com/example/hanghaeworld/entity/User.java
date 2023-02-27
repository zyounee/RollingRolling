package com.example.hanghaeworld.entity;


import com.example.hanghaeworld.dto.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Email
    @Column
    private String email;
    @Column
    private String image;
    @Column
    private String introduction;

    @Column
    private int likeCnt;

    @OneToMany(mappedBy = "likesUser", cascade = CascadeType.REMOVE)
    List<UserLike> likes = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    public User(String username, String password, String nickName, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.nickname = nickName;
        this.email = email;
        this.role = role;
    }

    public void update(UserRequestDto userRequestDto) {
        this.image = userRequestDto.getImage();
        this.introduction = userRequestDto.getIntroduction();
    }
}
