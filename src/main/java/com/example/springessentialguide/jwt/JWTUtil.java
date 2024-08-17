package com.example.springessentialguide.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    private final SecretKey secretKey; // 객체 키 생성

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        // 이 키는 특정하게 JWT에서 객체 타입으로 만들어서 저장.-> 키를 암호화 진행
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    /**
     * 검증 진행할 메서드 3개
     * 우리 서버에서 가져온 것이 맞는지 확인.
     */
    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    /**
     * 토큰 생성 메서드
     */
    public String createJwt(String username, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username) // claim으로 특정한 키에 대한 데이터를 넣어줌.
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰이 언제 발생
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료 기간
                .signWith(secretKey) // 토큰 시그니쳐를 만들어서 암호화 진행.
                .compact(); // 토큰 생성.
    }
}
