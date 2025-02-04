package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.KakaoLoginResponseDTO;
import com.umc.meetpick.service.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "카카오 로그인 API", description = "카카오 로그인 관련 API입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @Operation(summary = "카카오 로그인 요청")
    @GetMapping("/kakao")
    public ApiResponse<KakaoLoginResponseDTO> kakaoLogin(@RequestParam("code") String code) {
        KakaoLoginResponseDTO responseDTO = kakaoService.kakaoLogin(code);
        return ApiResponse.onSuccess(responseDTO);
    }
}
//localhost:8080/api/auth/kakao