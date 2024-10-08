package com.project.ity.api.user.request;

import com.project.ity.global.validation.phoneNumber.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class JoinRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @PhoneNumber
    private String phoneNumber;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickName;

    private List<Long> skillIds;

}
