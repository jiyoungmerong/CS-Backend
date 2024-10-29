package com.project.ity.domain.cs.service;

import com.project.ity.api.cs.request.CsAnswerCommentRequest;
import com.project.ity.api.cs.response.CsAnswerCommentResponse;
import com.project.ity.domain.cs.dto.CsAnswer;
import com.project.ity.domain.cs.dto.CsAnswerComment;
import com.project.ity.domain.cs.repository.CsAnswerRepository;
import com.project.ity.domain.cs.repository.CsAnswerCommentRepository;
import com.project.ity.global.exception.ErrorCode;
import com.project.ity.global.exception.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CsAnswerCommentService {
    private final CsAnswerCommentRepository csAnswerCommentRepository;

    private final CsAnswerRepository csAnswerRepository;

    public List<CsAnswerCommentResponse> getCommentsByAnswerId(Long answerId) {
        CsAnswer csAnswer = getCsAnswerById(answerId);

        List<CsAnswerComment> comments = csAnswerCommentRepository.findByCsAnswer(csAnswer);

        return comments.stream()
                .map(comment -> CsAnswerCommentResponse.of(comment.getId(), comment.getUserId(), comment.getComment()))
                .collect(Collectors.toList());
    }

    @Transactional
    public CsAnswerCommentResponse saveComment(CsAnswerCommentRequest request, String userId) {
        CsAnswer csAnswer = getCsAnswerById(request.getId());

        CsAnswerComment comment = CsAnswerComment.builder()
                .userId(userId)
                .comment(request.getComment())
                .csAnswer(csAnswer)
                .build();

        csAnswerCommentRepository.save(comment);

        return CsAnswerCommentResponse.of(comment.getId(), userId, comment.getComment());
    }

    private CsAnswer getCsAnswerById(Long answerId) {
        return csAnswerRepository.findById(answerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_REGISTER_CS_TOPIC));
    }
}
