package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.MajorDTO;
import com.umc.meetpick.service.CurrentUser;
import com.umc.meetpick.service.MajorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "전공 API", description = "전공 설정 관련 API입니다")
@RestController
@RequestMapping("/api/major")
@RequiredArgsConstructor
@Slf4j
public class MajorController {

    private final MajorService majorService;

    @Operation(summary = "전공 설정")
    @PostMapping("/set")
    public ApiResponse<MajorDTO.MajorResponseDTO> setMajor(
            @CurrentUser Long memberId,
            @RequestBody MajorDTO.MajorRequestDTO requestDTO) {

        log.info("🎓 전공 설정 요청 - memberId={}, subMajorId={}", memberId, requestDTO.getSubMajorId());
        return majorService.setMajor(memberId, requestDTO);
    }
}
