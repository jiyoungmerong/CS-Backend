package com.project.ity.api.cs.controller;

import com.project.ity.api.cs.request.CsAnswerCommentRequest;
import com.project.ity.api.cs.request.CsAnswerRequest;
import com.project.ity.api.cs.response.*;
import com.project.ity.domain.cs.service.CsAnswerCommentService;
import com.project.ity.domain.cs.service.CsAnswerService;
import com.project.ity.domain.cs.service.CsService;
import com.project.ity.global.common.RspTemplate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beta/cs")
public class CsController {

    private final CsService csService;

    private final CsAnswerService csAnswerService;

    private final CsAnswerCommentService csAnswerCommentService;

    @GetMapping("/cs-topics/today")
    public RspTemplate<TodayCsTopicResponse> selectTodayCsTopic() {
        TodayCsTopicResponse todayCsTopic = csService.selectCsTopic();

        return new RspTemplate<>(HttpStatus.OK, "오늘의 CS 주제를 성공적으로 불러왔습니다.", todayCsTopic);
    }

    @GetMapping("/cs-topics/{csId}/answers")
    public RspTemplate<List<CsAllAnswerResponse>> getAnswersByCsId(@PathVariable Long csId) {
        List<CsAllAnswerResponse> allAnswer = csAnswerService.getAnswersByCsId(csId);

        return new RspTemplate<>(HttpStatus.OK, "해당 주제의 모든 답변 리스트를 성공적으로 불러왔습니다.", allAnswer);
    }

    @PostMapping("/cs-topics/answers")
    public RspTemplate<CsAnswerResponse> saveCsAnswer(@RequestBody @Valid CsAnswerRequest request,
                                                      Principal principal){
        CsAnswerResponse csAnswerResponse = csAnswerService.createAnswer(request, principal.getName());

        return new RspTemplate<>(HttpStatus.OK, "CS 답변을 저장했습니다.", csAnswerResponse);
    }

    @GetMapping("/cs-answers/{answerId}/comments")
    public RspTemplate<List<CsAnswerCommentResponse>> getCommentsByAnswerId(@PathVariable Long answerId) {
        List<CsAnswerCommentResponse> comments = csAnswerCommentService.getCommentsByAnswerId(answerId);

        return new RspTemplate<>(HttpStatus.OK, "해당 답변의 모든 댓글 리스트를 성공적으로 불러왔습니다.", comments);
    }

    @PostMapping("/cs-answers/comments")
    public RspTemplate<CsAnswerCommentResponse> saveComment(@RequestBody @Valid CsAnswerCommentRequest request,
                                                            Principal principal) {
        CsAnswerCommentResponse csAnswerCommentResponse = csAnswerCommentService.saveComment(request, principal.getName());

        return new RspTemplate<>(HttpStatus.OK, "댓글이 저장되었습니다.", csAnswerCommentResponse);
    }

    @GetMapping("/cs-answers/top")
    public RspTemplate<List<CsAnswerRankResponse>> getTopRankings() {
        List<CsAnswerRankResponse> topRankings = csAnswerService.getCsTopRankings();
        return new RspTemplate<>(HttpStatus.OK, "CS 답변의 랭킹을 성공적으로 불러왔습니다.", topRankings);
    }
}
