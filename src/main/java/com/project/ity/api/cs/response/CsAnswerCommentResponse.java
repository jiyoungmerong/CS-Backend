package com.project.ity.api.cs.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CsAnswerCommentResponse {
    private Long id;

    private String userId;

    private String content;

    public static CsAnswerCommentResponse of(Long id, String userId, String content) {
        return new CsAnswerCommentResponse(id, userId, content);
    }
}
