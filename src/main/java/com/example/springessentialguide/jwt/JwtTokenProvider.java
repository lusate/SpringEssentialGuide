package com.example.springessentialguide.jwt;

import com.example.springessentialguide.jwt.dto.JwtTokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.example.springessentialguide.data.ExpiredTime.ACCESS_TOKEN_EXPIRE_TIME;
import static com.example.springessentialguide.data.ExpiredTime.REFRESH_TOKEN_EXPIRE_TIME;

/**
 * 터미널에 `openssl rand -hex -32` 입력해서 랜덤으로 암호 키를 생성한 후, 생성된 secret key를 application.yml에 설정.
 * 해당 키는 토큰의 암호화 복호화에 사용될 것. HS256 알고리즘을 사용하기 위해 32글자 이상으로 설정.
 *
 * JwtTokenProvider는 Spring Security와 JWT 토큰을 사용하여 인증과 권한 부여를 처리하는 클래스이다.
 * 이 클래스에서 JWT 토큰의 생성, 복호화, 검증 기능을 구현한다.
 */

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;

    // application.yml에서 secret 값 가져와서 key에 저장.
//    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
    public JwtTokenProvider(JwtProperties jwtProperties) {
        String secretKey = jwtProperties.getSecret();
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    /**
     * User 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
     * JWT와 관련된 유틸 기능을 제공.
     * 서명에 사용한 Key 값으로 복호화를 진행하기 때문에 동일한 Key 값이 필요하면 복호화 메소드 (getAuthentication), 검증 메서드 (validateToken) 가 필요하다.
     */
    @Transactional
    public JwtTokenDto generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining("."));
        log.error("authorities: {}", authorities);

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        log.error("accessToken: {}", accessToken);

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        log.error("refreshToken: {}", refreshToken);

        return JwtTokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    /**
     * Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
     */
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        log.error("claims: {}", claims);

        if(claims.get("auth") == null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, ",", authorities);
    }

    /**
     * 토큰 정보를 검증하는 메서드
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }


    /**
     * accessToken을 입력받아 `Claims` 객체를 반환.
     * Claims는 JWT의 본문(payload) 부분으로, JWT 토큰에 포함된 데이터.
     */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key) // JWT를 검증할 때 사용할 서명 키를 설정합니다. 이 키는 토큰이 생성될 때 사용된 비밀 키와 일치해야 합니다.
                    .build()
                    .parseClaimsJws(accessToken)// 주어진 accessToken을 파싱하여 JWS(JWT 서명) 객체를 반환
                    .getBody();
        } catch (ExpiredJwtException e) { // JWT 토큰이 만료된 경우 발생하는 예외
            return e.getClaims(); // 만료된 토큰에서도 클레임을 가져와 반환
        }
    }
}
