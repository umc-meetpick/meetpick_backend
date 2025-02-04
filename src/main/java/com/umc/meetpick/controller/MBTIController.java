package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.MBTIDTO;
import com.umc.meetpick.service.CurrentUser;
import com.umc.meetpick.service.MBTIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "MBTI API", description = "MBTI 설정 관련 API입니다")
@RestController
@RequestMapping("/api/mbti")
@RequiredArgsConstructor
@Slf4j
public class MBTIController {

    private final MBTIService mbtiService;

    @Operation(summary = "MBTI 설정")
    @PostMapping("/set")
    public ApiResponse<MBTIDTO.MBTIResponseDTO> setMBTI(
            @CurrentUser Long memberId,
            @RequestBody MBTIDTO.MBTIRequestDTO requestDTO) {

        log.info("🧠 MBTI 설정 요청 - memberId={}, MBTI={}", memberId, requestDTO.getMBTI());
        return mbtiService.setMBTI(memberId, requestDTO);
    }
}
