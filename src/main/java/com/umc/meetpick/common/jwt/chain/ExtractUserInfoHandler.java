package com.umc.meetpick.common.jwt.chain;

import com.umc.meetpick.common.jwt.factory.OAuth2UserInfoFactory;
import com.umc.meetpick.common.jwt.strategy.OAuth2UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

// 유저 정보를 받아오는 핸들러
@Slf4j
@Component
public class ExtractUserInfoHandler implements AuthenticationHandler {

    private AuthenticationHandler next;

    @Override
    public void setNext(AuthenticationHandler nextHandler) {
        this.next = nextHandler;
    }

    @Override
    public void handle(AuthContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("ExtractUserInfoHandler 시작");

        Map<String, Object> attributes = context.getAttributes();

        try {

            OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(attributes);

            log.info("🌐 로그인한 소셜 서비스: {}", userInfo.getSocialType());
            log.info("✅ OAuth2 로그인 사용자 socialId: {}", userInfo.getSocialId());

            context.setUserInfo(userInfo);

        } catch (Exception e) {
            log.error("⚠️ OAuth2User 정보를 가져오지 못했습니다.");
            response.sendRedirect("/login?error");
            return;
        }
        if (next != null) {
            next.handle(context, request, response);
        }
    }

}
