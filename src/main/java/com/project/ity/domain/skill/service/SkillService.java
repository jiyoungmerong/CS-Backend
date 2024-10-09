package com.project.ity.domain.skill.service;

import com.project.ity.api.skill.response.SkillListResponse;
import com.project.ity.domain.skill.dto.Skill;
import com.project.ity.domain.skill.repository.SkillRepository;
import com.project.ity.global.exception.ErrorCode;
import com.project.ity.global.exception.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillService {
    private final SkillRepository skillRepository;

    @Cacheable(value = "skills")
    public List<SkillListResponse> selectAllSkillList() {
        List<SkillListResponse> skills = skillRepository.findAllSkillList();
        if (skills.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_EXIT_SKILL);
        }
        return skills;
    }

    @Transactional
    @CacheEvict(value = "skills", allEntries = true)
    public void addSkill(String skillName) {
        Skill skill = Skill.builder()
                .skillName(skillName)
                .build();
        skillRepository.save(skill);
    }

    @Transactional
    @CacheEvict(value = "skills", allEntries = true)
    public void updateSkill(Long skillId, String skillName) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIT_SKILL));

        skill.updateSkillName(skillName);
        skillRepository.save(skill);
    }

    @Transactional
    @CacheEvict(value = "skills", allEntries = true)
    public void deleteSkill(Long skillId) {
        if (!skillRepository.existsById(skillId)) {
            throw new BusinessException(ErrorCode.NOT_EXIT_SKILL);
        }
        skillRepository.deleteById(skillId);
    }
}
