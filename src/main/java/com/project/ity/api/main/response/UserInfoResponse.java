package com.project.ity.api.main.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class    UserInfoResponse {

    private String nickName;

    public static UserInfoResponse of(String nickName) {
        return new UserInfoResponse(nickName);
    }
}
