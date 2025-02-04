package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.ProfileImageDTO;
import com.umc.meetpick.service.CurrentUser;
import com.umc.meetpick.service.ProfileImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "프로필 이미지 API", description = "프로필 이미지 선택 및 설정 API")
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@Slf4j
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    @Operation(summary = "프로필 이미지 설정")
    @PostMapping
    public ApiResponse<ProfileImageDTO.ProfileImageResponseDTO> setProfileImage(
            @CurrentUser Long memberId,  // @CurrentUser로 memberId 받기
            @Valid @RequestBody ProfileImageDTO.ProfileImageRequestDTO requestDTO) {

        log.info("🖼️ 프로필 이미지 설정 요청 - memberId={}, imageUrl={}", memberId, requestDTO.getImageUrl());

        if (memberId == null) {
            log.error("❌ memberId가 null입니다.");
        }

        return ApiResponse.onSuccess(profileImageService.setProfileImage(memberId, requestDTO));
    }
}
