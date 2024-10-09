package com.project.ity.domain.like.dto;

import com.project.ity.domain.cs.dto.CsAnswer;
import com.project.ity.domain.user.dto.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "likes") // SQL 예약어라서 지정해줘야함
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "answer_id")
    private CsAnswer csAnswer;

    @Builder
    public Like(User user, CsAnswer csAnswer) {
        this.user = user;
        this.csAnswer = csAnswer;
    }
}
