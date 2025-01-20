package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.MatchRequestListDto;
import com.umc.meetpick.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Match", description = "매칭 관련 API")  // [변경 1]
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matches") // URL 경로 설정
public class MatchController {

    private final RequestService requestService;

    @Operation(summary = "매칭 요청 목록 조회", description = "사용자에게 온 매칭 요청 목록을 페이징하여 조회합니다.") // [변경 2]
    @GetMapping
    public ApiResponse<MatchRequestListDto> getMatchRequests(
            @Parameter(description = "조회할 사용자의 ID", required = true) // [변경 3]
            @RequestParam Long memberId,
            @Parameter(description = "페이지 정보 (기본값: 10개, 생성일 기준 정렬)") // [변경 4]
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        MatchRequestListDto response = requestService.getMatchRequests(memberId, pageable);
        return ApiResponse.onSuccess(response);
    }
}