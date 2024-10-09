package com.project.ity.api.skill.controller;

import com.project.ity.api.skill.request.SkillRequest;
import com.project.ity.api.skill.response.SkillListResponse;
import com.project.ity.domain.skill.service.SkillService;
import com.project.ity.global.common.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beta/skill")
public class SkillController implements Serializable {
    private static final long serialVersionUID = 1L;

    private final SkillService skillService;

    @GetMapping("/skills")
    public RspTemplate<List<SkillListResponse>> selectAllSkillList() {
        List<SkillListResponse> skillList = skillService.selectAllSkillList();

        return new RspTemplate<>(HttpStatus.OK, "모든 스킬 정보를 가져오는데 성공했습니다.", skillList);
    }

    @PostMapping
    public RspTemplate<String> addSkill(@RequestBody SkillRequest request) {
        skillService.addSkill(request.getSkillName());
        return new RspTemplate<>(HttpStatus.OK, "스킬을 추가하였습니다.", request.getSkillName());
    }

    @PutMapping("/{skillId}")
    public RspTemplate<String> updateSkill(@PathVariable Long skillId, @RequestBody SkillRequest request) {
        skillService.updateSkill(skillId, request.getSkillName());
        return new RspTemplate<>(HttpStatus.OK, skillId + "번의 스킬이 업데이트 되었습니다.", request.getSkillName());
    }

    @DeleteMapping("/{skillId}")
    public RspTemplate<Void> deleteSkill(@PathVariable Long skillId) {
        skillService.deleteSkill(skillId);
        return new RspTemplate<>(HttpStatus.OK, skillId + "번의 스킬이 삭제 되었습니다.");
    }
}
