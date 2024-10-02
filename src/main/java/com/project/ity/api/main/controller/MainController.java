package com.project.ity.api.main.controller;

import com.project.ity.domain.main.service.MainService;
import com.project.ity.global.common.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beta/main")
public class MainController {

    private final MainService mainService;

    @GetMapping("/userInfo")
    public RspTemplate<String> selectUserInfo(Principal principal){

        String nickName = mainService.selectUserInfo(principal.getName());

        return new RspTemplate<>(HttpStatus.OK, "사용자의 정보를 불러왔습니다.", nickName);
    }

    @GetMapping("/csTopic")
    public RspTemplate<String> selectTodayCsTopic(@RequestParam Long id){

        String todayTopicContent = mainService.selectCsTopic(id);

        return new RspTemplate<>(HttpStatus.OK, "오늘의 CS 주제를 성공적으로 불러왔습니다.", todayTopicContent);
    }
}
