package com.project.ity.api.skill.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class SkillListResponse {

    private Long id;

    private String skillName;

    public static SkillListResponse of(Long id, String skillName) {
        return new SkillListResponse(id, skillName);
    }
}
