package com.example.springessentialguide.config;

import com.example.springessentialguide.data.service.CustomOAuth2UserService;
import com.example.springessentialguide.jwt.JWTFilter;
import com.example.springessentialguide.jwt.JWTUtil;
import com.example.springessentialguide.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
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
                                .userService(customOAuth2UserService)))) // Spring Security에서 OAuth 2.0 기반의 인증을 설정하는 메소드입니다. 이 설정은 사용자가 OAuth 2.0 프로바이더(예: Google, Facebook 등)를 통해 로그인할 수 있도록 해줍니다.
                .authorizeRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/signup", "/reissue").permitAll() // 모든 권한 허용
                        .requestMatchers("/admin").hasRole("ADMIN") // ADMIN 권한을 가진 사람만 허용
                        .anyRequest().authenticated()) // 그 외의 다른 요청들은 로그인한 사용자만 접근할 수 있다.
                // Session을 S1TATLESS 하게 만들어야 하기 때문에
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

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