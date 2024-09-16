package com.project.ity.domain.user.service;

import com.project.ity.api.user.request.JoinRequest;
import com.project.ity.api.user.response.JoinResponse;
import com.project.ity.domain.jwt.dto.TokenDto;
import com.project.ity.domain.jwt.service.TokenManager;
import com.project.ity.domain.skill.repository.SkillRepository;
import com.project.ity.domain.user.dto.User;
import com.project.ity.domain.user.repository.UserRepository;
import com.project.ity.global.exception.ErrorCode;
import com.project.ity.global.exception.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    private final SkillRepository skillRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenManager tokenManager;

    @Transactional
    public JoinResponse create(JoinRequest request) {
        validateJoinRequest(request);

        List<String> skillNames = validateAndGetSkillNames(request.getSkillIds());

        User user = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .nickName(request.getNickName())
                .skillList(request.getSkillIds())
                .build();

        userRepository.save(user);

        return JoinResponse.of(user.getUserId(), user.getPhoneNumber(), user.getNickName(), skillNames);
    }

    private void validateJoinRequest(JoinRequest request) {
        validateDuplicateField(request.getUserId(), userRepository::findByUserId, ErrorCode.ID_ALREADY_REGISTERED);
        validateDuplicateField(request.getNickName(), userRepository::findByNickName, ErrorCode.NICKNAME_ALREADY_REGISTERED);
        validateDuplicateField(request.getPhoneNumber(), userRepository::findByPhoneNumber, ErrorCode.NUMBER_ALREADY_REGISTERED);
    }

    private void validateDuplicateField(String fieldValue, Function<String, Optional<User>> findMethod, ErrorCode errorCode) {
        findMethod.apply(fieldValue)
                .ifPresent(user -> {
                    throw new BusinessException(errorCode);
                });
    }

    private List<String> validateAndGetSkillNames(List<Long> skillIds) {
        List<Long> invalidSkillIds = findInvalidSkillIds(skillIds);
        if (!invalidSkillIds.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_EXIT_SKILL);
        }

        return getSkillNames(skillIds);
    }

    private List<Long> findInvalidSkillIds(List<Long> skillIds) {
        return skillIds.stream()
                .filter(skillId -> !skillRepository.existsById(skillId))
                .collect(Collectors.toList());
    }

    private List<String> getSkillNames(List<Long> skillIds) {
        return skillIds.stream()
                .map(this::findSkillNameById)
                .collect(Collectors.toList());
    }

    private String findSkillNameById(Long skillId) {
        return skillRepository.findById(skillId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIT_SKILL))
                .getSkillName();
    }

    @Transactional
    public TokenDto login(String userId, String rawPassword) {
        User user = findByUserIdAndValidatePassword(userId, rawPassword);

        String audience = user.getUserId() + ":" + user.getNickName();
        TokenDto tokenDto = tokenManager.createTokenDto(audience);
        user.updateRefreshTokenAndExp(tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExp());

        tokenDto.setUserId(user.getUserId());
        return tokenDto;
    }


    private User findByUserIdAndValidatePassword(String userId, String rawPassword) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BusinessException(ErrorCode.MISMATCHED_SIGNIN_INFO);
        }
        return user;
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
