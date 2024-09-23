package com.project.ity.domain.user.dto;

import com.project.ity.global.common.BaseTimeEntity;
import com.project.ity.global.exception.ErrorCode;
import com.project.ity.global.exception.exceptions.JwtAuthException;
import com.project.ity.global.util.DateTimeUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity implements Serializable {
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

    @ElementCollection
    @CollectionTable(name = "user_skill", joinColumns = @JoinColumn(name = "userId"))
    @Column(name = "skillId")
    private List<Long> skillList = new ArrayList<>();

    @Column(length = 1000)
    private String refreshToken;

    private LocalDateTime tokenExp;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updateRefreshTokenAndExp(String refreshToken, Date refreshTokenExp) {
        this.refreshToken = refreshToken;
        this.tokenExp = DateTimeUtils.convertToLocalDateTime(refreshTokenExp);
    }

    public void logout(){
        this.refreshToken = "";
        this.tokenExp = LocalDateTime.now();
    }

    public void validateRefreshTokenExp() {
        if(tokenExp.isBefore(LocalDateTime.now())){
            throw new JwtAuthException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
    }

    @Builder
    private User(String userId, String password, String phoneNumber,
                 String nickName, List<Long> skillList){
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.skillList = skillList;
    }

}
