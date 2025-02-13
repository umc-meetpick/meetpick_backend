package com.umc.meetpick.controller;

import com.umc.meetpick.common.annotation.AuthUser;
import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.*;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.*;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    @GetMapping("/get-matches")
    public ApiResponse<MatchPageDto> getMatchRequests(@ModelAttribute PageRequestDto pageRequestDto, @AuthUser Long memberId) {

        Pageable pageable = pageRequestDto.toPageable();

        return ApiResponse.onSuccess(matchingService.getMatchRequests(memberId, pageRequestDto.getMateType(), pageable));
    }

    @Operation(summary = "추천 매칭 목록 조회", description = "사용자에게 적절한 메이트를 추천해줍니다") // [변경 2]
    @GetMapping("/recommendation")
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
    @GetMapping("/completed-match")
    public ApiResponse<MatchPageDto> getCompletedMatch(
            @ModelAttribute PageRequestDto pageRequestDto, @AuthUser Long memberId)
    {

        Pageable pageable = pageRequestDto.toPageable();

        return ApiResponse.onSuccess(matchingService.getCompletedMatches(memberId, pageRequestDto.getMateType(), pageable));
    }

    @Operation(summary = "찜한 목록 가져오기")
    @GetMapping("/like/{memberId}")
    public ApiResponse<List<MatchResponseDto>> getLikeRequest(@AuthUser Long memberId, @PathParam("mateType") MateType mateType) {
        return ApiResponse.onSuccess(requestService.getLikes(memberId, mateType));
    }

    @Operation(summary = "프로필 목록 조회", description = "메이트 타입별 전체 프로필 목록을 필터링하여 조회합니다.")
    @GetMapping("/profiles")
    public ApiResponse<ProfileDetailListResponseDto> getAllProfiles(
            //필수
            @AuthUser Long memberId,
            @RequestParam MateType mateType,

            // 공통 필터
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) StudentNumber studentNumber,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) Set<String> availableDays,
            @RequestParam(required = false) Set<String> availableTimes,

            // STUDY 필터
            @RequestParam(required = false) SubjectType subjectType,
            @RequestParam(required = false) CertificateType certificateType,

            // EXERCISE 필터
            @RequestParam(required = false) Set<ExerciseType> exerciseTypes,

            // MEAL 필터
            @RequestParam(required = false) Set<FoodType> foodTypes,

            @PageableDefault(size = 10) Pageable pageable
    ) {
        FilterRequestDTO filterRequest = FilterRequestDTO.builder()
                .gender(gender)
                .studentNumber(studentNumber)
                .minAge(minAge)
                .maxAge(maxAge)
                .availableDays(availableDays)
                .availableTimes(availableTimes)
                .subjectType(subjectType)
                .certificateType(certificateType)
                .exerciseTypes(exerciseTypes)
                .foodTypes(foodTypes)
                .build();

        return ApiResponse.onSuccess(
                matchingService.getAllProfiles(memberId, mateType, filterRequest, pageable)
        );
    }
}