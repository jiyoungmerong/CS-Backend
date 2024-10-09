package com.project.ity.domain.like.repository;

import com.project.ity.domain.cs.dto.CsAnswer;
import com.project.ity.domain.like.dto.Like;
import com.project.ity.domain.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndCsAnswer(User user, CsAnswer csAnswer);

}
