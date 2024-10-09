package com.project.ity.domain.like.service;

import com.project.ity.domain.cs.dto.CsAnswer;
import com.project.ity.domain.cs.repository.CsAnswerRepository;
import com.project.ity.domain.user.dto.User;
import com.project.ity.domain.user.repository.UserRepository;
import com.project.ity.global.exception.ErrorCode;
import com.project.ity.global.exception.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final UserRepository userRepository;

    private final CsAnswerRepository csAnswerRepository;

    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void insertLike(Long answerId, String userId) {
        User user = findUserById(userId);
        CsAnswer csAnswer = findCsAnswerById(answerId);

        String redisKey = "like:" + csAnswer.getId() + ":" + user.getId();

        if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
            throw new BusinessException(ErrorCode.ALREADY_EXISTS_LIKE);
        }

        redisTemplate.opsForValue().set(redisKey, String.valueOf(true));

        csAnswer.incrementLikeCount();
        updateLikesInDatabase(csAnswer);
    }

    @Transactional
    public void deleteLike(Long answerId, String userId) {
        User user = findUserById(userId);
        CsAnswer csAnswer = findCsAnswerById(answerId);

        String redisKey = "like:" + csAnswer.getId() + ":" + user.getId();

        if (Boolean.FALSE.equals(redisTemplate.hasKey(redisKey))) {
            throw new BusinessException(ErrorCode.NOT_EXISTS_LIKE);
        }

        redisTemplate.delete(redisKey);

        csAnswer.decrementLikeCount();
        csAnswerRepository.save(csAnswer);

        updateLikesInDatabase(csAnswer);
    }

    private void updateLikesInDatabase(CsAnswer csAnswer) {
        csAnswerRepository.save(csAnswer);
    }

    private User findUserById(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private CsAnswer findCsAnswerById(Long answerId) {
        return csAnswerRepository.findById(answerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_REGISTER_CS_TOPIC));
    }
}
