package com.umc.meetpick;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        System.out.println("🔹 Loaded JWT Secret from Environment: " + secret);
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes()); // 🔹 Base64 디코딩 없이 직접 사용
    }

    /**
     * 🔑 JWT 토큰 생성 (우리 DB의 회원 ID 기반)
     */
    public String generateToken(Long memberId) {
        return Jwts.builder()
                .setSubject(String.valueOf(memberId)) // 🔹 DB의 memberId 저장
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 🔑 JWT 토큰 검증
     */
    public Long validateToken(String token) {
        return Long.parseLong(Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject()); // 🔹 DB의 memberId 반환
    }
}
