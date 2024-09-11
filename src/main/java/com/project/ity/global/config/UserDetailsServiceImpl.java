package com.project.ity.global.config;

import com.project.ity.domain.user.dto.User;
import com.project.ity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 사용자 정보를 이메일로 조회
        Optional<User> optionalUser = userRepository.findByUserId(userId);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        User user = optionalUser.get();

        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
