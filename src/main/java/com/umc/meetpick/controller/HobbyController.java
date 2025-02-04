package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.HobbyDTO;
import com.umc.meetpick.service.HobbyService;
import com.umc.meetpick.service.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "취미 관련 API", description = "취미 관련 API입니다")
@RequiredArgsConstructor
@RequestMapping("/api/hobby")
@RestController
public class HobbyController {

    private final HobbyService hobbyService;

    @Operation(summary = "사용자의 취미 설정 API", description = "회원 ID와 선택한 취미 목록을 바탕으로 취미를 설정합니다.")
    @PostMapping("/set")
    public ApiResponse<HobbyDTO.HobbyResponseDTO> setHobbies(
            @RequestParam Long memberId,
            @CurrentUser Long currentUserId,
            @RequestBody HobbyDTO.HobbyRequestDTO hobbyRequestDTO) {


        hobbyRequestDTO.setMemberId(memberId);  // hobbyRequestDTO에 memberId 설정

        return hobbyService.setHobbies(hobbyRequestDTO);  // 서비스 호출
    }
}
