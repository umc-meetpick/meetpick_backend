package com.umc.meetpick.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "로그인 API", description = "로그인 API입니다")
@Controller
@RequestMapping("/login")
public class JoinController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

//    @GetMapping("/page")
//    public String loginPage(Model model) {
//        // 카카오 OAuth2 인증 URL 구성
//        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;
//        model.addAttribute("location", location); // 모델에 인증 URL 추가
//
//        return "login"; // 로그인 페이지 반환
//    }
}
