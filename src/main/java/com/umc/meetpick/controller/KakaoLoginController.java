package com.umc.meetpick.controller;

import com.umc.meetpick.dto.KakaoUserInfoResponseDTO;
import com.umc.meetpick.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @GetMapping("/callback")
    public void callback(@RequestParam("code") String code, HttpServletResponse response) {
        try {
            // AccessToken을 가져옵니다.
            String accessToken = kakaoService.getAccessTokenFromKakao(code);

            // 사용자 정보를 가져옵니다.
            KakaoUserInfoResponseDTO userInfo = kakaoService.getUserInfo(accessToken);

            // 사용자 정보를 데이터베이스에 저장하고 memberId 반환
            Long memberId = kakaoService.saveKakaoUser(userInfo);

            // /terms로 리다이렉트 (memberId를 포함)
            response.sendRedirect("/terms?memberId=" + memberId);
        } catch (Exception e) {
            log.error("Error during Kakao Login callback: {}", e.getMessage());
            try {
                response.sendRedirect("/error");
            } catch (IOException ex) {
                log.error("Error during redirection: {}", ex.getMessage());
            }
        }
    }
}
