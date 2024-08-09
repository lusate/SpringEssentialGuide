package com.example.springessentialguide.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtProperties {
    @Value("${jwt.secret}")
    private String secret;

}
