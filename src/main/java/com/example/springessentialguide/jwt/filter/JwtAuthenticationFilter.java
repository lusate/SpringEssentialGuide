package com.example.springessentialguide.jwt.filter;

import com.example.springessentialguide.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

/**
 * - 클라이언트 요청 시 JWT 인증을 하기 위해 설치하는 커스텀 필터로, UsernamePasswordAuthenticationFilter 이전에 실행할 것.
 * - 클라이언트로부터 들어오는 요청에서 JWT 토큰을 처리하고, 유효한 토큰인 경우 해당 토큰의 인증 정보(Authentication)를 SecurityContext에 저장하여
 * 인증된 요청을 처리할 수 있도록 한다.
 * - JWT를 통해 username + password 인증을 수행한다는 뜻.
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTH_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * resolveToken() 메서드를 사용하여 요청 헤더에서 JWT 토큰을 추출
     * JwtTokenProvider의 validateToken() 메서드로 JWT 토큰의 유효성 검증
     * 토큰 유효하면 getAuthentication() 으로 인증 객체 가져와서 SecurityContext에 저장. -> 요청을 처리하는 동안 인증 정보가 유지된다.
     * doFilter() 호출해서 다음 필터로 요청을 전달.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // HttpServletRequest에서 JWT 토큰 추출
        log.info("jwt Filter...");
        String token = resolveToken(request);
        log.info("jwtToken = {}", token);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.setAttribute("username", authentication.getName());
            log.info("set Authentication to security context for '{}', uri: '{}', Role '{}'",
                    authentication.getName(), ((HttpServletRequest) request).getRequestURI(), authentication.getAuthorities());
        }

        chain.doFilter(request, response);
    }

    /**
     * 주어진 HttpServletRequest에서 토큰 정보를 추출. Request Header로 토큰 정보 추출.
     * 즉 Authorization 헤더에서 Bearer 접두사로 시작하는 토큰을 추출하여 반환.
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);

        // null인 이유 : 헤더 누락 -> 클라이언트에서 요청을 보낼 때 Authorization 헤더를 포함하지 않음.
        //
        log.info("headerAuth 이게 자꾸 = {}", bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
