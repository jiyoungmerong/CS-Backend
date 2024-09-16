package com.project.ity.api.user.controller;

import com.project.ity.api.user.request.JoinRequest;
import com.project.ity.api.user.request.LoginRequest;
import com.project.ity.api.user.response.JoinResponse;
import com.project.ity.domain.jwt.dto.TokenDto;
import com.project.ity.domain.jwt.service.TokenValidator;
import com.project.ity.domain.user.service.UserService;
import com.project.ity.global.common.RspTemplate;
import com.project.ity.global.util.PrincipalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private final TokenValidator tokenValidator;

    @PostMapping("/join")
    public RspTemplate<JoinResponse> signUp(@RequestBody @Valid final JoinRequest request){
        JoinResponse joinResponse = userService.create(request);

        return new RspTemplate<>(HttpStatus.OK, "회원가입에 성공하였습니다.", joinResponse);

    }

    @PostMapping("/login")
    public RspTemplate<TokenDto> loginV2(@RequestBody @Valid final LoginRequest request) {
        TokenDto tokenDto = userService.login(request.getUserId(), request.getPassword());

        return new RspTemplate<>(HttpStatus.OK, "로그인에 성공하였습니다.", tokenDto);
    }

    @PostMapping("/logout")
    public RspTemplate<Void> logout(Principal principal) {
        userService.logout(PrincipalUtil.toUserId(principal));

        return new RspTemplate<>(HttpStatus.OK, "로그아웃 성공");
    }

    @PostMapping(value = "/token/reissue") // Access Token 재발급
    public RspTemplate<TokenDto> accessToken(HttpServletRequest httpServletRequest){

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        tokenValidator.validateBearer(authorizationHeader);

        String refreshToken = authorizationHeader.split(" ")[1];
        TokenDto tokenDto = userService.reissueByRefreshToken(refreshToken);

        return new RspTemplate<>(HttpStatus.OK, "토큰 재발급에 성공하였습니다.", tokenDto);
    }
}
