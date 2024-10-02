package com.project.ity.domain.jwt.service;

import com.project.ity.domain.jwt.constant.AuthScheme;
import com.project.ity.domain.jwt.constant.TokenType;
import com.project.ity.domain.jwt.dto.TokenDto;
import com.project.ity.global.exception.ErrorCode;
import com.project.ity.global.exception.exceptions.JwtAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class TokenManager {

    private final long accessTokenExpMillis;
    private final long refreshTokenExpMillis;
    private final Key key;

    @Autowired
    public TokenManager(
            @Value("${jwt.secret}") String tokenSecret
            , @Value("${jwt.access-token-expiration-time}") long accessTokenExpMillis
            , @Value("${jwt.refresh-token-expiration-time}") long refreshTokenExpMillis) {

        byte[] keyBytes = Decoders.BASE64.decode(tokenSecret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpMillis = accessTokenExpMillis;
        this.refreshTokenExpMillis = refreshTokenExpMillis;
    }

    public TokenDto createTokenDto(String userId) {
        Date accessTokenExp = createAccessTokenExp();
        Date refreshTokenExp = createRefreshTokenExp();

        String accessToken = createAccessToken(userId, accessTokenExp);
        String refreshToken = createRefreshToken(userId, refreshTokenExp);
        return TokenDto.builder()
                .authScheme(AuthScheme.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExp(accessTokenExp)
                .refreshToken(refreshToken)
                .refreshTokenExp(refreshTokenExp)
                .build();
    }

    private Date createAccessTokenExp() {
        return new Date(System.currentTimeMillis() + accessTokenExpMillis);
    }

    private Date createRefreshTokenExp() {
        return new Date(System.currentTimeMillis() + refreshTokenExpMillis);
    }

    private String createAccessToken(String userId, Date exp) {
        return Jwts.builder()
                .setSubject(TokenType.ACCESS.name())
                .setAudience(userId)
                .setIssuedAt(new Date())
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    private String createRefreshToken(String userId, Date exp) {
        return Jwts.builder()
                .setSubject(TokenType.REFRESH.name())
                .setAudience(userId)
                .setIssuedAt(new Date())
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    public Claims getTokenClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key).build()
                    .parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            throw new JwtAuthException(ErrorCode.NOT_VALID_TOKEN, e);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key).build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.info("잘못된 jwt token", e);
        } catch (JwtException e) {
            log.info("jwt token 검증 중 에러 발생", e);
        }
        return false;
    }

    public boolean isTokenExpired(Date exp) {
        Date now = new Date();
        return now.after(exp);
    }

    public String getTokenType(String token){
        Claims claims = getTokenClaims(token);
        return claims.getSubject();
    }
}
