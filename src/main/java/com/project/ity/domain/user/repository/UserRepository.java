package com.project.ity.domain.user.repository;

import com.project.ity.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickName(String nickName);
}
