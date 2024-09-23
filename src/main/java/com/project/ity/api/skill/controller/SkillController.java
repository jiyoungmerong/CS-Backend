package com.project.ity.api.skill.controller;

import com.project.ity.api.skill.response.SkillListResponse;
import com.project.ity.domain.skill.service.SkillService;
import com.project.ity.global.common.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/skill")
public class SkillController {

    private final SkillService skillService;

    @GetMapping("/list")
    public RspTemplate<List<SkillListResponse>> selectAllSkillList() {
        List<SkillListResponse> skillList = skillService.selectAllSkillList();

        return new RspTemplate<>(HttpStatus.OK, "모든 스킬 정보를 가져오는데 성공했습니다.", skillList);
    }
}
