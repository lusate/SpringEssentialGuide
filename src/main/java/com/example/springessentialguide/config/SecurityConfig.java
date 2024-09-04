package com.example.springessentialguide.config;

import com.example.springessentialguide.data.repository.RefreshRepository;
import com.example.springessentialguide.jwt.CustomLogoutFilter;
import com.example.springessentialguide.jwt.JWTFilter;
import com.example.springessentialguide.jwt.JWTUtil;
import com.example.springessentialguide.jwt.LoginFilter;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((auth) -> auth.disable())
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable())
                .authorizeRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/signup", "/reissue").permitAll() // 모든 권한 허용
                        .requestMatchers("/admin").hasRole("ADMIN") // ADMIN 권한을 가진 사람만 허용
                        .anyRequest().authenticated()) // 그 외의 다른 요청들은 로그인한 사용자만 접근할 수 있다.
                // Session을 S1TATLESS 하게 만들어야 하기 때문에
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class)

                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        // 허용할 HTTP 메서드를 설정합니다. *로 설정하면 모든 메서드(GET, POST, PUT, DELETE 등)를 허용합니다.
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        // 클라이언트가 자격 증명(예: 쿠키, HTTP 인증)을 포함할 수 있도록 허용합니다. 이 설정이 true일 경우, allowedOrigins에 명시된 출처만 사용할 수 있습니다.
                        configuration.setAllowCredentials(true);
                        // 요청에서 허용할 헤더를 설정합니다. *로 설정하면 모든 헤더를 허용합니다.
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L); // CORS preflight 요청의 캐시 시간을 설정합니다. 여기서는 3600초(1시간) 동안 캐시됩니다.

                        // Set-Cookie 헤더를 클라이언트에서 접근할 수 있도록 노출합니다.
                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        // Authorization 헤더도 클라이언트에서 접근할 수 있도록 노출합니다.
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                }));

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