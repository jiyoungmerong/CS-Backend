package com.project.ity.domain.user.service;

import com.project.ity.api.user.request.JoinRequest;
import com.project.ity.api.user.response.JoinResponse;
import com.project.ity.domain.jwt.dto.TokenDto;
import com.project.ity.domain.jwt.service.TokenManager;
import com.project.ity.domain.user.dto.User;
import com.project.ity.domain.user.repository.UserRepository;
import com.project.ity.global.exception.ErrorCode;
import com.project.ity.global.exception.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenManager tokenManager;

    @Transactional
    public JoinResponse create(JoinRequest request) {
        validateDuplicateUserId(request.getUserId());
        validateDuplicateNickName(request.getNickName());
        validateDuplicatePhoneNumber(request.getPhoneNumber());

        User user = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .nickName(request.getNickName())
                .skill(request.getSkill())
                .build();

        userRepository.save(user);

        return JoinResponse.of(user.getUserId(), user.getPhoneNumber(), user.getNickName(), user.getSkill());
    }

    private void validateDuplicateUserId(String userId) {
        userRepository.findByUserId(userId)
                .ifPresent(m -> {
                    throw new BusinessException(ErrorCode.ID_ALREADY_REGISTERED);
                });
    }

    private void validateDuplicateNickName(String nickName) {
        userRepository.findByNickName(nickName)
                .ifPresent(m -> {
                    throw new BusinessException(ErrorCode.NICKNAME_ALREADY_REGISTERED);
                });
    }

    private void validateDuplicatePhoneNumber(String phoneNumber) {
        userRepository.findByPhoneNumber(phoneNumber)
                .ifPresent(m -> {
                    throw new BusinessException(ErrorCode.NUMBER_ALREADY_REGISTERED);
                });
    }

    @Transactional
    public TokenDto login(String userId, String rawPassword) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));


        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BusinessException(ErrorCode.MISMATCHED_SIGNIN_INFO);
        }

        String audience = user.getUserId() + ":" + user.getNickName();

        TokenDto tokenDto = tokenManager.createTokenDto(audience);
        user.updateRefreshTokenAndExp(tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExp());

        tokenDto.setUserId(user.getUserId());
        return tokenDto;
    }

    @Transactional
    public TokenDto reissueByRefreshToken(String refreshToken) {
        User user = findByRefreshToken(refreshToken);
        user.validateRefreshTokenExp();

        String audience = user.getUserId() + ":" + user.getNickName();

        TokenDto tokenDto = tokenManager.createTokenDto(audience);
        user.updateRefreshTokenAndExp(tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExp());

        tokenDto.setUserId(user.getUserId());
        return tokenDto;
    }

    private User findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }

    @Transactional
    public void logout(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.logout();
    }
}
