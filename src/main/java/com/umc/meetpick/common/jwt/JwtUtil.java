package com.umc.meetpick.common.jwt;

import com.umc.meetpick.common.exception.GeneralException;
import com.umc.meetpick.common.response.status.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class JwtUtil {

    // TODO refresh 토큰 사용하기
    private final SecretKey secretKey;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 24; // 24 * 24 시간 // TODO 배포 시 변경
    private static final Map<Long, String> tokenCache = new ConcurrentHashMap<>(); // ✅ 토큰 캐시 추가

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        log.info("🔹 Loaded JWT Secret from Environment: {}", secret);
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 🔑 JWT 토큰 생성 (회원 ID 기반)
     */
    public String generateToken(Long memberId) {
        if (tokenCache.containsKey(memberId)) {
            return tokenCache.get(memberId); // ✅ 기존 토큰이 있으면 재사용
        }

        String token = Jwts.builder()
                .setSubject(String.valueOf(memberId)) // 사용자 ID 저장
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명
                .compact();

        tokenCache.put(memberId, token); // ✅ 새로운 토큰 저장
        return token;
    }

    public String getMemberId(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 🔑 JWT 토큰 검증 및 회원 ID 반환
     */
    public Long validateToken(String token) {
        try {
            return Long.parseLong(Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject());

        } catch (ExpiredJwtException e) {
            log.error("❌ JWT 토큰이 만료되었습니다.");
            throw new RuntimeException("JWT 토큰이 만료되었습니다.");
        } catch (MalformedJwtException | SignatureException e) {
            log.error("❌ 유효하지 않은 JWT 토큰입니다.");
            throw new RuntimeException("유효하지 않은 JWT 토큰입니다.");
        } catch (Exception e) {
            log.error("❌ JWT 검증 오류: {}", e.getMessage());
            throw new RuntimeException("JWT 검증 오류");
        }
    }
}