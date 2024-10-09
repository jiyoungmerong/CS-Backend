package com.project.ity.domain.skill.dto;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;

    public void updateSkillName(String skillName){
        this.skillName = skillName;
    }

    @Builder
    public Skill(String skillName){
        this.skillName = skillName;
    }
}
