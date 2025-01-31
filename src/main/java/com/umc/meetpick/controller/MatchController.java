package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.AlarmResponseDto;
import com.umc.meetpick.dto.MatchRequestListDto;
import com.umc.meetpick.dto.MatchResponseDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.service.MatchingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Match", description = "매칭 관련 API")  // [변경 1]
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matches") // URL 경로 설정
public class MatchController {

    private final MatchingService matchingService;

    // 작동 됨
    @Operation(summary = "매칭 요청 목록 조회", description = "사용자에게 온 매칭 요청 목록을 페이징하여 조회합니다.") // [변경 2]
    @GetMapping
    public ApiResponse<MatchRequestListDto> getMatchRequests(
            @Parameter(description = "조회할 사용자의 ID", required = true) // [변경 3]
            @RequestParam Long memberId,
            @Parameter(description = "페이지 정보 (기본값: 10개, 생성일 기준 정렬)") // [변경 4]
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        MatchRequestListDto response = matchingService.getMatchRequests(memberId, pageable);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "추천 매칭 목록 조회", description = "사용자에게 적절한 메이트를 추천해줍니다") // [변경 2]
    @GetMapping("recommendation")
    public ApiResponse<List<MatchResponseDto>> getRecommendation(
            @PathParam("mateType") MateType mateType, Member member)
    {
        return ApiResponse.onSuccess(matchingService.match(member, mateType));
    }

    @Operation(summary = "알람 목록 받아오기", description = "알람을 받아옵니다") // [변경 2]
    @GetMapping("alarm")
    public ApiResponse<List<AlarmResponseDto>> getAlarm(
            @PathParam("mateType") MateType mateType, Member member)
    {
        return ApiResponse.onSuccess(matchingService.getAlarms(member, mateType));
    }
}