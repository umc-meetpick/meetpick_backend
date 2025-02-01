package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "약관동의 API", description = "약관동의 API입니다")
@RestController
@RequestMapping("/api/terms")
@RequiredArgsConstructor
@Slf4j
public class TermsController {

    @Operation(summary = "약관 동의 확인")
    @PostMapping("/check")
    public ApiResponse<Map<String, Object>> checkTermsAgreement(@RequestParam("memberId") Long memberId,
                                                                @RequestParam("agreed") boolean agreed) {
        log.info("📄 약관 동의 확인 - memberId: {}, agreed: {}", memberId, agreed);

        if (!agreed) {
            throw new IllegalArgumentException("약관에 동의해야 가입이 가능합니다.");
        }

        // ✅ 응답에 memberId 포함하여 반환
        Map<String, Object> response = Map.of(
                "message", "약관 동의 확인 완료",
                "memberId", memberId
        );

        return ApiResponse.onSuccess(response);
    }
}
