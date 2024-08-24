package com.example.springessentialguide.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/")
    public String mainAPI() {
        return "main route";
    }
}

