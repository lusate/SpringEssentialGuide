package com.example.springessentialguide.data.service.impl;

import com.example.springessentialguide.data.dto.MemberDto;
import com.example.springessentialguide.data.repository.MemberRepository;
import com.example.springessentialguide.data.service.MemberService;
import com.example.springessentialguide.jwt.JwtTokenProvider;
import com.example.springessentialguide.jwt.dto.JwtTokenDto;
import com.example.springessentialguide.securityDto.SignInDto;
import com.example.springessentialguide.securityDto.SignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 인증을 위한 서비스 구현.
     */
    @Override
    public JwtTokenDto signIn(String username, String password) {
        // username + password를 기반으로 Authentication 객체 생성
        // authentication은 인증 여부를 확인하는 authenticated 값이 false
        // `UsernamePasswordAuthenticationToken` 는 로그인 시도된 username과 password로 인증을 처리하기 위한 형태.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행 -> Spring Security가 AuthenticationManagerBuilder를 통해 인증을 처리.
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.error("authentication.toString() : {}", authentication.toString());

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenDto jwtToken = jwtTokenProvider.generateToken(authentication);

        log.error("jwtTokenProvider : {}", jwtToken);

        // 인증 정보를 기반으로 JWT 토큰 생성.
        return jwtToken;
    }

    @Override
    public MemberDto signUp(SignUpDto signUpDto) {
        if (memberRepository.existsByUsername(signUpDto.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 이름입니다!!");
        }

        String encode = passwordEncoder.encode(signUpDto.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        return MemberDto.toDto(memberRepository.save(signUpDto.toEntity(encode, roles)));
    }

}
