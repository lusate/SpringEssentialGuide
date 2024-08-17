package com.example.springessentialguide.jwt;

import com.example.springessentialguide.data.dto.CustomUserDetails;
import com.example.springessentialguide.data.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // JWT를 request 해서 검증을 진행하는데 그러기 위해서는 JWTUtil을 통해서 필터를 검증할 메서드를 가져와야 한다.
        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");
        log.info("authorization Header : {}", authorization);


        if( authorization == null || !authorization.startsWith("Bearer ")) {
//        if (authorization == null && !authorization.startsWith("Bearer ")) {
            log.error("Authorization header is missing");
            filterChain.doFilter(request, response); // chain 방식으로 엮여있는 필터들에서 이 필터를 종료하고 다음 필터로 넘겨준다.
            return;
        }

        String token = authorization.split(" ")[1]; // 즉 "Bearer " 이후 부분
        log.info("token : {}", token);
        if (jwtUtil.isExpired(token)) {
            log.error("JWT Token expired");
            filterChain.doFilter(request, response);
            return; // 메서드 종료
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        Member member = new Member();
        member.setUsername(username);
        member.setPassword("temppassword"); //
        member.setRole(role);

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(member);
        // 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 세션에 사용자 등록 -> 이 authToken을 SecurityContextHolder에 넣으면 이 요청에 대해서 유저 세션을 생성할 수 있습니다.
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
