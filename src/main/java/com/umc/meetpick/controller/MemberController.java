package com.umc.meetpick.controller;

import com.umc.meetpick.common.annotation.AuthUser;
import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.MemberDetailResponseDto;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.dto.RegisterDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "멤버 관련 API", description = "멤버 관련 API입니다")
@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 상세 프로필 보기", description = "사용자 상세 프로필 보기") // [변경 2]
    @GetMapping("detail/{memberId}")
    public ApiResponse<MemberDetailResponseDto> getMemberDetail(@AuthUser Long memberId){
        return ApiResponse.onSuccess(memberService.getMemberDetail(memberId));
    }

    // 회원 기본 정보 저장
    // TODO memberId에서 member로 AuthUser 바꾸기
    @Operation(summary = "회원 기본 정보 저장")
    @PostMapping("/signup")
    public ApiResponse<RegisterDTO.SignupSuccessDTO> saveMember(@AuthUser Long memberId, @RequestBody RegisterDTO.SignUpDTO signUpDTO) {
        return ApiResponse.onSuccess(memberService.saveMember(memberId, signUpDTO));
    }

    // 1차 프로필 저장
    @Operation(summary = "1차 프로필 저장")
    @PostMapping("/signup/profile")
    public ApiResponse<RegisterDTO.SignupSuccessDTO> saveMemberProfile(@AuthUser Long memberId, @RequestBody RegisterDTO.SignUpProfileDTO signUpProfileDTO) {
        return ApiResponse.onSuccess(memberService.saveMemberProfile(memberId, signUpProfileDTO));
    }

    // 이메일 인증 코드 발송
    @Operation(summary = "이메일 인증 코드 요청")
    @PostMapping("/verify/sendCode")
    public ApiResponse<String> sendVerificationCode(@RequestBody RegisterDTO.EmailVerificationRequestDTO requestDTO) {
        return ApiResponse.onSuccess(memberService.sendVerificationCode(requestDTO));
    }

    @Operation(summary = "이메일 인증 코드 검증")
    @PostMapping("/verify/verifyCode")
    public ApiResponse<String> verifyCode(@AuthUser Long memberId, @RequestBody RegisterDTO.EmailVerificationCodeDTO codeDTO) {
        return ApiResponse.onSuccess(memberService.verifyEmailCode(memberId, codeDTO));
    }

    // 닉네임 중복 검사 API
    @Operation(summary = "닉네임 중복 검사", description = "닉네임 중복을 검사합니다.")
    @GetMapping("/nickname/check")
    public ApiResponse<String> checkNickname(@AuthUser Long memberId, String nickname) {

        return ApiResponse.onSuccess(memberService.nickDuplicate(memberId, nickname));  // ProfileService로 호출
    }
}
