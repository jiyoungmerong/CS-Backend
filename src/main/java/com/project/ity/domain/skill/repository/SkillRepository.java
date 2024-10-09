package com.project.ity.domain.skill.repository;

import com.project.ity.api.skill.response.SkillListResponse;
import com.project.ity.domain.skill.dto.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findById(Long id);

    @Query("SELECT new com.project.ity.api.skill.response.SkillListResponse(s.id, s.skillName) FROM Skill s")
    List<SkillListResponse> findAllSkillList();
}
