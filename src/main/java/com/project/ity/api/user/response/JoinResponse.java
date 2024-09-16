package com.project.ity.api.user.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class JoinResponse {
    private String userId;
    private String phoneNumber;
    private String nickName;
    private List<String> skillNames;

    public static JoinResponse of(String userId, String phoneNumber, String nickName, List<String> skillNames) {
        return new JoinResponse(userId, phoneNumber, nickName, skillNames);
    }
}
