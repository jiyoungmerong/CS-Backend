package com.project.ity.api.skill.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SkillRequest {
    @NotBlank(message = "스킬명을 작성해주세요.")
    @NotNull
    private String skillName;
}
