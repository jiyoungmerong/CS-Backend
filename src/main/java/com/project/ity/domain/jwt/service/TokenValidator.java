package com.project.ity.domain.jwt.service;

import com.project.ity.domain.jwt.constant.AuthScheme;
import com.project.ity.global.exception.ErrorCode;
import com.project.ity.global.exception.exceptions.JwtAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class TokenValidator {
    public void validateBearer(String authHeader) {
        if(!StringUtils.hasText(authHeader)){
            throw new JwtAuthException(ErrorCode.NOT_EXISTS_AUTH_HEADER);
        }

        String[] authorizations = authHeader.split(" ");
        if(authorizations.length < 2 || (!AuthScheme.BEARER.getType().equals(authorizations[0]))){
            throw new JwtAuthException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }
    }
}
