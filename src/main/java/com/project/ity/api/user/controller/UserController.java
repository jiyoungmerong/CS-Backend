package com.project.ity.api.user.controller;

import com.project.ity.api.user.request.JoinRequest;
import com.project.ity.api.user.response.JoinResponse;
import com.project.ity.domain.user.service.UserService;
import com.project.ity.global.common.RspTemplate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public RspTemplate<JoinResponse> signUp(@RequestBody @Valid final JoinRequest request){
        JoinResponse joinResponse = userService.create(request);

        return new RspTemplate<>(HttpStatus.OK, "회원가입에 성공하였습니다.", joinResponse);

    }


}
