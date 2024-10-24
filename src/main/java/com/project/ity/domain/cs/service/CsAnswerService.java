package com.project.ity.domain.cs.service;

import com.project.ity.api.cs.request.CsAnswerRequest;
import com.project.ity.api.cs.response.CsAllAnswerResponse;
import com.project.ity.api.cs.response.CsAnswerRankResponse;
import com.project.ity.api.cs.response.CsAnswerResponse;
import com.project.ity.domain.cs.dto.CS;
import com.project.ity.domain.cs.dto.CsAnswer;
import com.project.ity.domain.cs.repository.CsAnswerRepository;
import com.project.ity.domain.cs.repository.CsRepository;
import com.project.ity.global.exception.ErrorCode;
import com.project.ity.global.exception.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CsAnswerService {
    private final CsRepository csRepository;

    private final CsAnswerRepository csAnswerRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    public List<CsAllAnswerResponse> getAnswersByCsId(Long csId) {
        CS cs = csRepository.findById(csId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_REGISTER_CS_TOPIC));

        List<CsAnswer> answers = csAnswerRepository.findByCs(cs);

        return convertToCsAllAnswerResponses(answers);
    }

    private List<CsAllAnswerResponse> convertToCsAllAnswerResponses(List<CsAnswer> answers) {
        return answers.stream()
                .map(answer -> CsAllAnswerResponse.of(answer.getId(), answer.getUserId(), answer.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional
    public CsAnswerResponse createAnswer(CsAnswerRequest request, String userId) {
        CS cs = getCsById(request.getCsId());
        validateUserAnswer(userId, request.getCsId());

        CsAnswer csAnswer = CsAnswer.builder()
                .userId(userId)
                .cs(cs)
                .content(request.getContent())
                .build();

        csAnswerRepository.save(csAnswer);
        initializeRedisViewCount(csAnswer.getId());

        return CsAnswerResponse.of(cs.getCsSubject(), request.getContent(), userId);
    }

    public void incrementViewCount(Long answerId, String userId) {
        String redisKey = getRedisKey("viewCount", answerId);
        String userViewKey = getRedisKey("userView", answerId);

        if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(userViewKey, userId))) {
            return;
        }

        redisTemplate.opsForValue().increment(redisKey, 1);
        redisTemplate.opsForSet().add(userViewKey, userId);
    }

    public Long getViewCount(Long answerId) {
        String redisKey = getRedisKey("viewCount", answerId);
        return (Long) redisTemplate.opsForValue().get(redisKey);
    }

    public List<CsAnswerRankResponse> getCsTopRankings() {
        List<Object[]> csAnswerRanking = csAnswerRepository.findTop3ByOrderByLikeCountDesc();

        if (csAnswerRanking.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_EXISTS_RANKING);
        }

        return csAnswerRanking.stream()
                .map(record -> CsAnswerRankResponse.of(
                        (String) record[0], // nickName
                        (Integer) record[1] // likeCount
                ))
                .collect(Collectors.toList());
    }

    // Helper Methods
    private void validateUserAnswer(String userId, Long csId) {
        boolean alreadyAnswered = csAnswerRepository.existsByUserIdAndCsId(userId, csId);
        if (alreadyAnswered) {
            throw new BusinessException(ErrorCode.ALREADY_EXIST_CS_ANSWER);
        }
    }

    private void initializeRedisViewCount(Long answerId) {
        String redisKey = getRedisKey("viewCount", answerId);
        redisTemplate.opsForValue().set(redisKey, 0); // 초기 조회수 0
    }

    private CS getCsById(Long csId) {
        return csRepository.findById(csId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_REGISTER_CS_TOPIC));
    }

    private String getRedisKey(String prefix, Long id) {
        return prefix + ":" + id;
    }
}
