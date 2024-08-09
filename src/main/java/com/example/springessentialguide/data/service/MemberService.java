package com.example.springessentialguide.data.service;

import com.example.springessentialguide.data.dto.MemberDto;
import com.example.springessentialguide.jwt.dto.JwtTokenDto;
import com.example.springessentialguide.securityDto.SignUpDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    JwtTokenDto signIn(String username, String password);

    MemberDto signUp(SignUpDto signUpDto);
}
