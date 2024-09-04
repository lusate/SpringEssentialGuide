package com.example.springessentialguide.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 컨트롤러 단에서 보내준 데이터를 받을 수 있도록 하는 클래스
 */
@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**") // 내부 값은 특정한 모든 경로에서 매핑
                .exposedHeaders("Set-Cookie") // 노출할 헤더값
                .allowedOrigins("http://localhost:3000");
    }
}
