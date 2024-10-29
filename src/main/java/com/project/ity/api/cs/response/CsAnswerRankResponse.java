package com.project.ity.api.cs.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CsAnswerRankResponse {
    private String nickName;

    private int likeCount;

    public static CsAnswerRankResponse of(String nickName, int likeCount){
        return new CsAnswerRankResponse(nickName, likeCount);
    }
}
