package com.project.ity.domain.cs.dto;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CsAnswerComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String comment;

    @Column(nullable = false, updatable = false)
    private String regDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cs_answer_id")
    private CsAnswer csAnswer;

    @Builder
    public CsAnswerComment(String userId, String comment, CsAnswer csAnswer) {
        this.userId = userId;
        this.comment = comment;
        this.csAnswer = csAnswer;
        this.regDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
