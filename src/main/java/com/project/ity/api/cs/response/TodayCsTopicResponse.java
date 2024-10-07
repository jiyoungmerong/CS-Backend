package com.project.ity.api.cs.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class TodayCsTopicResponse {
    private String titleContent;

    public static TodayCsTopicResponse of(String titleContent) {
        return new TodayCsTopicResponse(titleContent);
    }
}
