package com.project.ity.domain.skill.repository;

import com.project.ity.domain.skill.dto.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findById(Long id);
}
