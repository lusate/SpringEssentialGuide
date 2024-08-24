package com.example.springessentialguide.controller;

import com.example.springessentialguide.data.dto.SignUpDto;
import com.example.springessentialguide.data.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpDto> signUp(SignUpDto signUpDto) {
        log.info("Sign Up DTO: {}", signUpDto.getUsername());

        try{
            memberService.signUpProcess(signUpDto);
            return ResponseEntity.ok(signUpDto);
        } catch(Exception e){
            log.error("회원가입 실패: {}", e.getMessage());
            return status(BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/admin")
    public String adminP() {
        return "Admin Controller";
    }

    @GetMapping("/")
    public String homeP() {
        return "Home Controller";
    }
}