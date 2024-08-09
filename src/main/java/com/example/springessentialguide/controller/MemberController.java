package com.example.springessentialguide.controller;

import com.example.springessentialguide.data.dto.MemberDto;
import com.example.springessentialguide.jwt.securityutil.SecurityUtil;
import com.example.springessentialguide.securityDto.SignInDto;
import com.example.springessentialguide.jwt.dto.JwtTokenDto;
import com.example.springessentialguide.data.service.MemberService;
import com.example.springessentialguide.securityDto.SignUpDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/sign-in")
    public JwtTokenDto signIn(@RequestBody SignInDto signInDto) {
        String username = signInDto.getUsername();
        String password = signInDto.getPassword();

        log.error("username: {}", username);
        log.error("password: {}", password);

        JwtTokenDto jwtToken = memberService.signIn(username, password);
        log.error("JWT Token: {}", jwtToken);

//        log.info("request username = {}, password = {}", username, password);
        log.error("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }

    /**
     * 추가로 어떤 회원이 API를 요청했는지 쉽게 조회할 수 있는 클래스.
     * getCurrentUsername()을 호출하여 현재 요청을 보낸 회원의 username을 쉬벡 얻을 수 있다.
     */
    @PostMapping("/test2")
    public String test2() {
        return SecurityUtil.getCurrentUserEmail();
    }


    @PostMapping("/sign-up")
    public ResponseEntity<MemberDto> signup(@RequestBody SignUpDto signUpDto) {
        MemberDto memberDto = memberService.signUp((signUpDto));

        return ResponseEntity.ok(memberDto);
    }
}
