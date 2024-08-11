package com.project.ity.domain.user.dto;

import com.project.ity.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String nickName;

    private String skill; // todo list 형식으로 변환해야함 1:n 형식으로 변경

    @Column(length = 1000)
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    @Builder
    private User(String userId, String password, String phoneNumber,
                 String nickName, String skill){
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.skill = skill;
    }
}
