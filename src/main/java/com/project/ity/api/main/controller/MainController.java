package com.project.ity.api.main.controller;

import com.project.ity.api.cs.response.TodayCsTopicResponse;
import com.project.ity.api.main.response.UserInfoResponse;
import com.project.ity.domain.cs.service.CsService;
import com.project.ity.domain.main.service.MainService;
import com.project.ity.global.common.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beta/main")
public class MainController {

    private static final LocalDate BASE_DATE = LocalDate.of(2024, 10, 7);

    private final MainService mainService;

    private final CsService csService;

    @GetMapping("/userInfo")
    public RspTemplate<UserInfoResponse> selectUserInfo(Principal principal) {
        UserInfoResponse userInfo = mainService.selectUserInfo(principal.getName());

        return new RspTemplate<>(HttpStatus.OK, "사용자의 정보를 불러왔습니다.", userInfo);
    }

    @GetMapping("/today")
    public RspTemplate<TodayCsTopicResponse> selectTodayCsTopic() {
        long id = calculateTodayId();
        TodayCsTopicResponse todayCsTopic = csService.selectCsTopic(id);

        return new RspTemplate<>(HttpStatus.OK, "오늘의 CS 주제를 성공적으로 불러왔습니다.", todayCsTopic);
    }

    private long calculateTodayId() {
        LocalDate today = LocalDate.now();
        return ChronoUnit.DAYS.between(BASE_DATE, today) + 1;
    }

//    @GetMapping("/has-answers")
//    public RspTemplate<Boolean> hasAnswersInLastThreeMonths(Principal principal) {
//        boolean hasAnswers = mainService.getAnswersForOneMonthRange(principal.getName());
//        return ResponseEntity.ok(new RspTemplate<>(HttpStatus.OK.value(), "조회 완료", hasAnswers));
//    }

}
