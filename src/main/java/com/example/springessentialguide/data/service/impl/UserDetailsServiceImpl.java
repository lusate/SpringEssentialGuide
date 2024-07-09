package com.example.springessentialguide.data.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 사용자 인증을 처리하기 위해 사용되는 핵심 메서드.
     * 주어진 사용자 이름에 대한 정보를 로드하여 UserDetails 객체로 반환하는 역할을 한다.
     * 즉 주어진 사용자 이름을 사용하여 DB나 다른 데이터 소스로부터 사용자를 검색한다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
