package com.project.ity.api.user.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class JoinResponse {
    private String userId;

    private String password;

    private String phoneNumber;

    private String nickName;

    private String skill;

    public static JoinResponse of(String userId, String password, String phoneNumber,
                                  String nickName, String skill){
        return new JoinResponse(userId, password, phoneNumber, nickName, skill);
    }

}
