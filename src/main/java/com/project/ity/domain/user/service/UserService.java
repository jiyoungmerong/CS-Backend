package com.project.ity.domain.user.service;

import com.project.ity.api.user.request.JoinRequest;
import com.project.ity.api.user.response.JoinResponse;
import com.project.ity.domain.user.User;
import com.project.ity.domain.user.repository.UserRepository;
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

    @Transactional
    public JoinResponse create(JoinRequest request) {
        User user = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .nickName(request.getNickName())
                .skill(request.getSkill())
                .build();

        userRepository.save(user);

        return JoinResponse.of(user.getUserId(), user.getPassword(), user.getPhoneNumber(),
                user.getNickName(), user.getSkill());
    }
}
