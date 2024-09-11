package com.example.springessentialguide.config;

<<<<<<< HEAD
import com.example.springessentialguide.data.repository.RefreshRepository;
import com.example.springessentialguide.jwt.CustomLogoutFilter;
=======
>>>>>>> 1d1de56 (rebase)
import com.example.springessentialguide.jwt.JWTFilter;
import com.example.springessentialguide.jwt.JWTUtil;
import com.example.springessentialguide.jwt.LoginFilter;
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
<<<<<<< HEAD
import org.springframework.security.web.authentication.logout.LogoutFilter;
=======
>>>>>>> 1d1de56 (rebase)

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
<<<<<<< HEAD
    private final RefreshRepository refreshRepository;
=======
>>>>>>> 1d1de56 (rebase)

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((auth) -> auth.disable())
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable())
                .authorizeRequests((auth) -> auth
<<<<<<< HEAD
                        .requestMatchers("/login", "/", "/signup", "/reissue").permitAll() // 모든 권한 허용
=======
                        .requestMatchers("/login", "/", "/signup").permitAll() // 모든 권한 허용
>>>>>>> 1d1de56 (rebase)
                        .requestMatchers("/admin").hasRole("ADMIN") // ADMIN 권한을 가진 사람만 허용
                        .anyRequest().authenticated()) // 그 외의 다른 요청들은 로그인한 사용자만 접근할 수 있다.
                // Session을 S1TATLESS 하게 만들어야 하기 때문에
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
<<<<<<< HEAD
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);
=======
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
>>>>>>> 1d1de56 (rebase)

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