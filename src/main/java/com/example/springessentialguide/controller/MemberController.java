package com.example.springessentialguide.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@ResponseBody
@RequiredArgsConstructor
public class MemberController {
    @GetMapping("/my")
    public String myPage() {
        return "my";
    }
}

