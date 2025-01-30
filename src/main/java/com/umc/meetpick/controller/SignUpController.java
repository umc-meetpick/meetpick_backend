package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.SignUpDTO;
import com.umc.meetpick.service.SignUpService;
import com.umc.meetpick.common.response.status.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원가입 API", description = "회원가입 API입니다")
@RestController
@RequestMapping("/api/signup")
@RequiredArgsConstructor
@Slf4j
public class SignUpController {

    private final SignUpService signUpService;

    @Operation(summary = "회원가입 페이지 호출")
    @GetMapping
    public ApiResponse<String> showSignupPage(@RequestParam(value = "memberId", required = false) Long memberId) {
        log.info("📝 회원가입 페이지 호출 - memberId={}", memberId);

        if (memberId == null) {
            log.warn("⚠ memberId가 전달되지 않음. 약관 동의 페이지로 리다이렉트");
            return ApiResponse.onFailure(ErrorCode._BAD_REQUEST.getCode(), ErrorCode._BAD_REQUEST.getMessage(), null);
        }

        return ApiResponse.onSuccess("회원가입 페이지 호출 성공");
    }

    @Operation(summary = "회원 기본 정보 저장")
    @PostMapping("/profile")
    public ApiResponse<SignUpDTO> saveMemberProfile(
            @RequestParam("memberId") Long memberId,
            @RequestBody SignUpDTO signUpDTO) {
        log.info("회원 기본 정보 저장 요청 - memberId={}", memberId);

        return signUpService.processSignup(memberId, signUpDTO);
    }
}
