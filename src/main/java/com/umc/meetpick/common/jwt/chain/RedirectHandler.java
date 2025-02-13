package com.umc.meetpick.common.jwt.chain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class RedirectHandler implements AuthenticationHandler {

    private AuthenticationHandler next;

    @Value("${front.redirect-url}")
    private String url;

    @Override
    public void setNext(AuthenticationHandler nextHandler) {
        this.next = nextHandler;
    }

    @Override
    public void handle(AuthContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("RedirectHandler 실행");

        String token = context.getToken();

        String redirectUrl;

        if (context.isNewMember()) {
            // 신규 회원이면 회원가입 페이지로 리디렉션
            redirectUrl = url + "/signup?token=" + token;
        } else {
            // 기존 회원이면 메인 페이지로 리디렉션
            redirectUrl = url + "/looking?token=" + token;
        }

        response.sendRedirect(redirectUrl);

        log.info("redirect to {}", url);

        if (next != null) {
            next.handle(context, request, response);
        }
    }
}
