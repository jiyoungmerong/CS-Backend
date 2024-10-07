package com.project.ity.domain.main.service;

import com.project.ity.api.main.response.UserInfoResponse;
import com.project.ity.domain.cs.repository.CsAnswerRepository;
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

    private final CsAnswerRepository csAnswerRepository;

    public UserInfoResponse selectUserInfo(String userId) {
        String nickName = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND))
                .getNickName();

        return UserInfoResponse.of(nickName);
    }

//    public boolean getAnswersForOneMonthRange(String userId) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//
//        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
//        String oneMonthAgoStr = oneMonthAgo.format(formatter);
//
//        LocalDateTime oneMonthLater = LocalDateTime.now().plusMonths(1);
//        String oneMonthLaterStr = oneMonthLater.format(formatter);
//
//        return csAnswerRepository.existsByUserIdAndCreatedAtBetween(userId, oneMonthAgoStr, oneMonthLaterStr);
//    }
}
