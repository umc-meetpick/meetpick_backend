package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.SignupSuccessDTO;
import com.umc.meetpick.service.SignupSuccessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원가입 성공 API", description = "회원가입 완료 후 성공 정보를 반환합니다.")
@RestController
@RequestMapping("/api/signup-success")
@RequiredArgsConstructor
@Slf4j
public class SignupSuccessController {

    private final SignupSuccessService signupSuccessService;

    /**
     * ✅ 회원가입 성공 페이지 (HTML 렌더링)
     */
    @Operation(summary = "회원가입 완료 페이지")
    @GetMapping
    public String signupSuccessPage(@RequestParam Long memberId, Model model) {
        SignupSuccessDTO successInfo = signupSuccessService.getSignupSuccessInfo(memberId);
        model.addAttribute("title", "회원가입 완료");
        model.addAttribute("successInfo", successInfo);
        return "signupSuccess"; // signupSuccess.html 페이지 반환
    }

    /**
     * ✅ 회원가입 성공 정보를 JSON 응답으로 반환
     */
    @Operation(summary = "회원가입 완료 정보 JSON 반환")
    @GetMapping("/json")
    public ApiResponse<SignupSuccessDTO> getSignupSuccessInfo(@RequestParam Long memberId) {
        SignupSuccessDTO successInfo = signupSuccessService.getSignupSuccessInfo(memberId);
        return ApiResponse.onSuccess(successInfo);
    }
}
