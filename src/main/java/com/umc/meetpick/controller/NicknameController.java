package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.NicknameDTO;
import com.umc.meetpick.service.CurrentUser;
import com.umc.meetpick.service.NicknameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@Tag(name = "닉네임 API", description = "닉네임 관련 API입니다")
@RestController
@RequestMapping("/api/nickname")
@RequiredArgsConstructor
@Slf4j
public class NicknameController {

    private final NicknameService nicknameService;

    @Operation(summary = "닉네임 중복 검사")
    @GetMapping("/check")
    public ApiResponse<NicknameDTO.NicknameCheckResponseDTO> checkNickname(
            @CurrentUser Long memberId,  // JWT로 인증된 memberId 자동 추출
            @RequestParam("nickname") String nickname) {

        log.info("✅ 닉네임 중복 검사 - memberId={}, nickname={}", memberId, nickname);
        return nicknameService.checkNicknameAvailability(memberId, nickname);
    }

    @Operation(summary = "닉네임 설정")
    @PostMapping("/set")
    public ApiResponse<NicknameDTO.NicknameResponseDTO> setNickname(
            @CurrentUser Long memberId,  // JWT로 인증된 memberId 자동 추출
            @RequestBody NicknameDTO.NicknameRequestDTO requestDTO) {

        log.info("✅ 닉네임 설정 요청 - memberId={}, nickname={}", memberId, requestDTO.getNickname());
        return nicknameService.setNickname(memberId, requestDTO);
    }
}
