package com.project.ity.domain.cs.repository;

import com.project.ity.domain.cs.dto.CsAnswer;
import com.project.ity.domain.cs.dto.CsAnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CsAnswerCommentRepository extends JpaRepository<CsAnswerComment, Long> {
    List<CsAnswerComment> findByCsAnswer(CsAnswer csAnswer);
}
