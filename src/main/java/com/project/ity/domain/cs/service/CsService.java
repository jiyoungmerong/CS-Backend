package com.project.ity.domain.cs.service;

import com.project.ity.api.cs.response.TodayCsTopicResponse;
import com.project.ity.domain.cs.repository.CsRepository;
import com.project.ity.global.exception.ErrorCode;
import com.project.ity.global.exception.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CsService {
    private final CsRepository csRepository;

    private static final LocalDate START_DATE = LocalDate.of(2024, 10, 24);

    public TodayCsTopicResponse selectCsTopic() {
        long id = calculateDailyTopicId();

        String todayContent = csRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_REGISTER_CS_TOPIC))
                .getCsSubject();

        return TodayCsTopicResponse.of(todayContent);
    }

    private long calculateDailyTopicId() {
        LocalDate today = LocalDate.now();
        return ChronoUnit.DAYS.between(START_DATE, today) + 1;
    }
}
