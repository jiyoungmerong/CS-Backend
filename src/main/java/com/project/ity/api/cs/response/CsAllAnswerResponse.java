package com.project.ity.api.cs.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CsAllAnswerResponse {
    private Long id;

    private String userId;

    private String content;

    public static CsAllAnswerResponse of(Long id, String userId, String content){
        return new CsAllAnswerResponse(id, userId, content);
    }
}
