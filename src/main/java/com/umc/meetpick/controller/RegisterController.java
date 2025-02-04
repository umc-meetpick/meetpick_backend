package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.*;
import com.umc.meetpick.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "회원가입 API", description = "회원가입 관련 API입니다")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class RegisterController {

    private final RegisterService registerService;

    // 회원가입 페이지 호출
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

    // 회원 기본 정보 저장
    @Operation(summary = "회원 기본 정보 저장")
    @PostMapping("signup/profile")
    public ApiResponse<RegisterDTO.SignUpDTO> saveMemberProfile(
            @RequestParam("memberId") Long memberId,
            @RequestBody RegisterDTO.SignUpDTO signUpDTO) {
        log.info("회원 기본 정보 저장 요청 - memberId={}", memberId);
        return registerService.saveMemberProfile(memberId, signUpDTO);
    }

    // 이메일 인증 코드 발송
    @Operation(summary = "이메일 인증 코드 요청")
    @PostMapping("/verify/sendCode")
    public ApiResponse<String> sendVerificationCode(@RequestBody RegisterDTO.EmailVerificationRequestDTO requestDTO) {
        log.info("📧 이메일 인증 코드 요청 - memberId={}, email={}, university={}",
                requestDTO.getMemberId(), requestDTO.getEmail(), requestDTO.getUnivName());

        return registerService.sendVerificationCode(
                requestDTO.getMemberId(), requestDTO.getEmail(), requestDTO.getUnivName());
    }

    // 이메일 인증 코드 검증
    @Operation(summary = "이메일 인증 코드 검증")
    @PostMapping("/verify/verifyCode")
    public ApiResponse<String> verifyCode(@RequestBody RegisterDTO.EmailVerificationCodeDTO codeDTO) {
        log.info("🔍 이메일 인증 코드 검증 - memberId={}, email={}, university={}, code={}",
                codeDTO.getMemberId(), codeDTO.getEmail(), codeDTO.getUnivName(), codeDTO.getVerificationCode());

        return registerService.verifyEmailCode(
                codeDTO.getMemberId(), codeDTO.getEmail(), codeDTO.getUnivName(), codeDTO.getVerificationCode());
    }

    // 약관 동의 확인
    @Operation(summary = "약관 동의 확인")
    @PostMapping("/terms/check")
    public ApiResponse<Map<String, Object>> checkTermsAgreement(@RequestParam("memberId") Long memberId,
                                                                @RequestParam("agreed") boolean agreed) {
        log.info("📄 약관 동의 확인 - memberId: {}, agreed: {}", memberId, agreed);

        if (!agreed) {
            throw new IllegalArgumentException("약관에 동의해야 가입이 가능합니다.");
        }

        // 응답에 memberId 포함하여 반환
        Map<String, Object> response = Map.of(
                "message", "약관 동의 확인 완료",
                "memberId", memberId
        );

        return ApiResponse.onSuccess(response);
    }

    // 회원가입 성공 페이지
    @Operation(summary = "회원가입 완료 페이지")
    @GetMapping("/success")
    public ApiResponse<RegisterDTO.SignupSuccessDTO> getSignupSuccessInfo(@RequestParam Long memberId) {
        return registerService.getSignupSuccessInfo(memberId);
    }

}
