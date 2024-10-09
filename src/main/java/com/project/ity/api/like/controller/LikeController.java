package com.project.ity.api.like.controller;

import com.project.ity.domain.like.service.LikeService;
import com.project.ity.global.common.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beta/like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/likes/{answerId}")
    public RspTemplate<String> insert(@PathVariable Long answerId, Principal principal) {
        likeService.insertLike(answerId, principal.getName());
        return new RspTemplate<>(HttpStatus.OK, "공감을 눌렀습니다.");
    }

    @DeleteMapping("/likes/{answerId}")
    public RspTemplate<String> delete(@PathVariable Long answerId, Principal principal) {
        likeService.deleteLike(answerId, principal.getName());
        return new RspTemplate<>(HttpStatus.OK, "공감을 취소하였습니다.");
    }

}
