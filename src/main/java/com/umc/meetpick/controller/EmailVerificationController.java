package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.EmailVerificationCodeDTO;
import com.umc.meetpick.dto.EmailVerificationRequestDTO;
import com.umc.meetpick.service.EmailVerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "대학교 이메일 인증 API", description = "대학교 이메일 인증 관련 API입니다")
@RestController
@RequestMapping("/api/verify")
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    /**
     * ✅ 이메일 인증 코드 발송
     */
    @Operation(summary = "이메일 인증 코드 요청")
    @PostMapping("/sendCode")
    public ApiResponse<String> sendVerificationCode(@RequestBody EmailVerificationRequestDTO requestDTO) {
        log.info("📧 이메일 인증 코드 요청 - memberId={}, email={}, university={}",
                requestDTO.getMemberId(), requestDTO.getEmail(), requestDTO.getUnivName());

        return emailVerificationService.sendVerificationCode(
                requestDTO.getMemberId(), requestDTO.getEmail(), requestDTO.getUnivName());
    }

    /**
     * ✅ 이메일 인증 코드 검증 및 대학교 정보 업데이트
     */
    @Operation(summary = "이메일 인증 코드 검증")
    @PostMapping("/verifyCode")
    public ApiResponse<String> verifyCode(@RequestBody EmailVerificationCodeDTO codeDTO) {
        log.info("🔍 이메일 인증 코드 검증 - memberId={}, email={}, university={}, code={}",
                codeDTO.getMemberId(), codeDTO.getEmail(), codeDTO.getUnivName(), codeDTO.getVerificationCode());

        return emailVerificationService.verifyCode(
                codeDTO.getMemberId(), codeDTO.getEmail(), codeDTO.getUnivName(), codeDTO.getVerificationCode());
    }
}
