package com.example.springessentialguide.jwt;

import com.example.springessentialguide.data.dto.CustomUserDetails;
import com.example.springessentialguide.data.entity.Member;
<<<<<<< HEAD
import io.jsonwebtoken.ExpiredJwtException;
=======
>>>>>>> 1d1de56 (rebase)
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
<<<<<<< HEAD
import java.io.PrintWriter;
=======
>>>>>>> 1d1de56 (rebase)

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

<<<<<<< HEAD
    /**
     * JWT Access Token 검증
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access"); // key를 access로 저장했었음.

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {
            filterChain.doFilter(request, response); // 다음 필터로 넘김.
            return;
        }

        // 토큰 만료 여부 확인, 만료 시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            return;
        }

        // 토큰이 Access인지 Refresh인지 확인 (발급 시 Payload에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) { // Access 토큰이 아니면
            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // username, role 값을 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        Member member = new Member();
        member.setUsername(username);
        member.setRole(role);
        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        // 로그인 진행
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        // 해당 유저를 SecurityContextHolder에 등록하면 일시적인 세션 생성.
        // 요청에 대해서 로그인된 상태로 변경.
        SecurityContextHolder.getContext().setAuthentication(authToken);

=======
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
>>>>>>> 1d1de56 (rebase)
        filterChain.doFilter(request, response);
    }
}
