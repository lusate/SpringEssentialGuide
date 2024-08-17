package com.example.springessentialguide.data.service;

import com.example.springessentialguide.data.dto.CustomUserDetails;
import com.example.springessentialguide.data.entity.Member;
import com.example.springessentialguide.data.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository; // DB에 접근할 Repository

    /**
     * DB에서 특정 유저를 조회해서 반환해주는 메서드
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);
        log.info("member: {}", member);
        if(member != null) {
            log.info("로그인 성공");
            return new CustomUserDetails(member);
        }

        return null;
    }
}
