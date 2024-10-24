package com.project.ity.api.cs.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CsAnswerResponse {
    private String csSubject;

    private String content;

    private String userId;

    public static CsAnswerResponse of(String csSubject, String content, String userId) {
        return new CsAnswerResponse(csSubject, content, userId);
    }
}
