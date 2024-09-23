package com.project.ity.domain.skill.service;

import com.project.ity.api.skill.response.SkillListResponse;
import com.project.ity.domain.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillService {
    private final SkillRepository skillRepository;

    public List<SkillListResponse> selectAllSkillList() {
        return skillRepository.findAll()
                .stream()
                .map(skill -> SkillListResponse.of(skill.getId(), skill.getSkillName()))
                .collect(Collectors.toList());
    }
}
