package com.umc.meetpick.controller;

import com.umc.meetpick.common.annotation.AuthUser;
import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.*;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.service.matching.MatchingService;
import com.umc.meetpick.service.request.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Match", description = "매칭 관련 API")  // [변경 1]
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matches") // URL 경로 설정
@Slf4j
public class MatchController {

    private final MatchingService matchingService;
    private final RequestService requestService;

    // 작동 됨
    @Operation(summary = "매칭 요청 목록 조회", description = "사용자에게 온 매칭 요청 목록을 페이징하여 조회합니다.") // [변경 2]
    @GetMapping
    public ApiResponse<MatchRequestListDto> getMatchRequests(@ModelAttribute PageRequestDto pageRequestDto, @AuthUser Long memberId) {

        Pageable pageable = pageRequestDto.toPageable();

        return ApiResponse.onSuccess(matchingService.getMatchRequests(memberId, pageRequestDto.getMateType(), pageable));
    }

    @Operation(summary = "추천 매칭 목록 조회", description = "사용자에게 적절한 메이트를 추천해줍니다") // [변경 2]
    @GetMapping("recommendation")
    public ApiResponse<List<MatchResponseDto>> getRecommendation(
            @PathParam("mateType") MateType mateType, @AuthUser Long memberId)
    {
        return ApiResponse.onSuccess(matchingService.match(memberId, mateType));
    }

    @Operation(summary = "알람 목록 받아오기", description = "알람을 받아옵니다") // [변경 2]
    @GetMapping("/alarm")
    public ApiResponse<AlarmDto.AlarmPageResponseDto> getAlarm(@ModelAttribute AlarmDto.AlarmRequestDto alarmRequestDto, @AuthUser Long memberId)
    {

        log.info(alarmRequestDto.getMateType());

        Pageable pageable = alarmRequestDto.toPageable();

        log.info(String.valueOf(pageable.getPageNumber()));

        return ApiResponse.onSuccess(matchingService.getAlarms(alarmRequestDto.getMateType(), pageable, memberId));
    }

    @Operation(summary = "매칭이 완료된 리스트 받아오기", description = "매칭 완료된 리스트 받아옴") // [변경 2]
    @GetMapping("completed-match")
    public ApiResponse<List<MatchRequestDto>> getCompletedMatch(
            @PathParam("mateType") MateType mateType, @AuthUser Long memberId)
    {
        return ApiResponse.onSuccess(matchingService.getCompletedMatches(memberId, mateType));
    }

    @Operation(summary = "찜한 목록 가져오기")
    @GetMapping("/like/{memberId}")
    public ApiResponse<List<MatchResponseDto>> getLikeRequest(@AuthUser Long memberId, @PathParam("mateType") MateType mateType) {
        return ApiResponse.onSuccess(requestService.getLikes(memberId, mateType));
    }
}