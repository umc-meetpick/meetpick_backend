package com.umc.meetpick.common.config;

import com.umc.meetpick.common.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    //TODO 리프레시 토큰 추가 및 권한 추가

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable) // UI를 사용하는 것을 기본값으로 가진 시큐리티 설정 비활성화
                .cors(AbstractHttpConfigurer::disable) // CORS 비활성화
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable) // H2 콘솔을 위한 프레임 허용
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) // stateless로 설정
                //TODO 나중에 이 부분 리팩토링
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint ->
                                endpoint.baseUri("/oauth2/authorize"))
                        .redirectionEndpoint(endpoint ->
                                endpoint.baseUri("/login/oauth2/code/*"))
                        .successHandler(oAuth2AuthenticationSuccessHandler) // ✅ OAuth2 성공 핸들러 설정
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                )
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers( "/**").permitAll()
                        /*"/sign-api/**", "/swagger-ui/**", "/swagger-ui.html/**", "/v3/api-docs/**", "/oauth2/**", "/h2-console/**", "/api/university/**", "/api/members/random-user", "/login/**"*/
                        .anyRequest().authenticated()) // 로그인 관련만 허용
                // 애플리케이션에 들어오는 요청에 대한 사용권한을 체크한다.
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
                ) // 인증 실패 및 권한이 없는 경우
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
