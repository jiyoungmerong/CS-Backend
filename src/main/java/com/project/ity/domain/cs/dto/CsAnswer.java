package com.project.ity.domain.cs.dto;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CsAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "cs_id")
    private CS cs;

    private String userId;

    private int likeCount = 0;

    @Column(nullable = false, updatable = false)
    private String createdAt;

    @OneToMany(mappedBy = "csAnswer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CsAnswerComment> comments = new ArrayList<>();

    // todo 등록한 일시 추가

    @Builder
    public CsAnswer(String content, CS cs, String userId) {
        this.content = content;
        this.cs = cs;
        this.userId = userId;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

}
