package com.umc.meetpick.common.config;

import com.umc.meetpick.common.exception.handler.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 🔹 CSRF 비활성화
                .cors(cors -> {}) // 🔹 CORS 설정 추가 (필요시 Custom 설정 가능)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/terms/**", "/api/signup/**", "/api/verify/**","/api/signup-success/**").permitAll() // 🔹 기존 허용 경로 유지
                        .requestMatchers("/api/auth/**").permitAll() // ✅ 카카오 로그인 API 인증 없이 접근 허용
                        .requestMatchers("/oauth2/**", "/login/**", "/default-ui.css").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureUrl("/login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                );

        return http.build();
    }
}
