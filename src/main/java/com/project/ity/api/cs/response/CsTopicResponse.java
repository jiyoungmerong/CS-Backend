package com.project.ity.api.cs.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CsTopicResponse {
    private String csSubject;

    public static CsTopicResponse of(String csSubject) {
        return new CsTopicResponse(csSubject);
    }
}
