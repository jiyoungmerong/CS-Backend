package com.project.ity.global.config;

import com.project.ity.domain.jwt.constant.AuthScheme;
import com.project.ity.domain.jwt.constant.TokenType;
import com.project.ity.domain.jwt.service.TokenManager;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filter(request.getHeader(HttpHeaders.AUTHORIZATION));

        filterChain.doFilter(request, response);
    }

    private void filter(String authHeader){
        if(!StringUtils.hasText(authHeader))
            return;

        String[] authorizations = authHeader.split(" ");

        if(authorizations.length < 2 || (!AuthScheme.BEARER.getType().equals(authorizations[0]))){
            return;
        }

        String token = authorizations[1];

        if (! tokenManager.validateToken(token)) {
            return;
        }

        String tokenType = tokenManager.getTokenType(token);
        if(!TokenType.ACCESS.name().equals(tokenType))
            return;

        Claims claims = tokenManager.getTokenClaims(token);

        if (tokenManager.isTokenExpired(claims.getExpiration())) {
            return;
        }

        String audience = claims.getAudience();

        Authentication authentication = new UsernamePasswordAuthenticationToken(audience, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
