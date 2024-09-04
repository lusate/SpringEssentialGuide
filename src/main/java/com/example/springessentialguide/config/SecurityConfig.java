package com.example.springessentialguide.config;

import com.example.springessentialguide.service.CustomOAuth2UserService;
import com.example.springessentialguide.jwt.JWTFilter;
import com.example.springessentialguide.jwt.JWTUtil;
import com.example.springessentialguide.oauth2.CustomSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;
    private final CustomOAuth2UserService customOAuth2UserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((auth) -> auth.disable())
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable()) // 사용자가 아이디와 비밀번호를 HTTP 헤더에 포함시켜 서버에 인증 요청을 보내는 방식입니다.
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)))
                        .successHandler(customSuccessHandler)) // Spring Security에서 OAuth 2.0 기반의 인증을 설정하는 메소드입니다. 이 설정은 사용자가 OAuth 2.0 프로바이더(예: Google, Facebook 등)를 통해 로그인할 수 있도록 해줍니다.

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/signup", "/reissue").permitAll() // 모든 권한 허용
                        .requestMatchers("/admin").hasRole("ADMIN") // ADMIN 권한을 가진 사람만 허용
                        .anyRequest().authenticated()) // 그 외의 다른 요청들은 로그인한 사용자만 접근할 수 있다.
                // Session을 S1TATLESS 하게 만들어야 하기 때문에
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JWTFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class)

                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // 프론트 서버 주소
                        configuration.setAllowedMethods(Collections.singletonList("*")); // CRUD 메서드 요청 허용
                        configuration.setAllowCredentials(true); // credential도 가져올 수 있도록 허용
                        configuration.setAllowedHeaders(Collections.singletonList("*")); // 어떤 헤더를 가져올 수 있도록 할 것인지 세팅
                        configuration.setMaxAge(3600L); //

                        // 우리 쪽에서 데이터를 줄 경우 웹 페이지에서 보이게 할 수 있는 방법
                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                }
                ));


        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}