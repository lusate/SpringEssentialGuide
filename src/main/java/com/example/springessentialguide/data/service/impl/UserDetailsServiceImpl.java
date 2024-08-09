package com.example.springessentialguide.data.service.impl;

import com.example.springessentialguide.data.entity.Member;
import com.example.springessentialguide.data.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 인증을 처리하기 위해 사용되는 핵심 메서드.
     * 주어진 사용자 이름에 대한 정보를 로드하여 UserDetails 객체로 반환하는 역할을 한다.
     * 즉 주어진 사용자 이름을 사용하여 DB나 다른 데이터 소스로부터 사용자를 검색한다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user details for userId: {}", username);
        log.info("Loading user details for userId: {}", passwordEncoder.encode("1234"));

        return memberRepository.findByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다"));
    }


    private UserDetails createUserDetails(Member member) {
        return Member.builder()
                .username(member.getUsername())
                .password(passwordEncoder.encode(member.getPassword()))
                .roles(List.of(member.getRoles().toArray(new String[0]))) // 수정
                .build();
    }
}
