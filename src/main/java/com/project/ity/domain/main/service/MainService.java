package com.project.ity.domain.main.service;

import com.project.ity.domain.cs.repository.CsRepository;
import com.project.ity.domain.user.repository.UserRepository;
import com.project.ity.global.exception.ErrorCode;
import com.project.ity.global.exception.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainService {

    private final UserRepository userRepository;

    private final CsRepository csRepository;

    public String selectUserInfo(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND))
                .getNickName();
    }

    public String selectCsTopic(Long id) {
        return csRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_REGISTER_CS_TOPIC))
                .getTitleContent();
    }
}
