package com.umc.meetpick.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.meetpick.common.jwt.chain.AuthContext;
import com.umc.meetpick.common.jwt.chain.ExtractUserInfoHandler;
import com.umc.meetpick.common.jwt.chain.LoginOrRegistrationHandler;
import com.umc.meetpick.common.jwt.chain.RedirectHandler;
import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.common.response.status.SuccessCode;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.SocialType;
import com.umc.meetpick.repository.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ExtractUserInfoHandler extractUserInfoHandler;
    private final LoginOrRegistrationHandler loginOrRegistrationHandler;
    private final RedirectHandler redirectHandler;
    public static final Map<Long, String> tokenStorage = new ConcurrentHashMap<>(); // ✅ JWT 토큰 저장소

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        log.info("✅ OAuth2 인증 성공 - 사용자 처리 시작");

        if (!(authentication.getPrincipal() instanceof OAuth2User oAuth2User)) {
            log.error("OAuth2User 정보를 가져오지 못했습니다.");
            response.sendRedirect("/login?error");
            return;
        }

        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("OAuth2 사용자 속성: {}", attributes);

        // AuthContext 생성
        AuthContext context = new AuthContext(attributes);

        extractUserInfoHandler.setNext(loginOrRegistrationHandler);
        loginOrRegistrationHandler.setNext(redirectHandler);
        extractUserInfoHandler.handle(context, request, response);

    }
}
