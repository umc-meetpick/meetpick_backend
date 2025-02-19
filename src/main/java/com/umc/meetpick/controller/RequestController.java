package com.umc.meetpick.controller;

import com.umc.meetpick.common.annotation.AuthUser;
import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.service.request.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// 매칭 관련 controller
@Tag(name = "매칭 관련 API", description = "매칭 관련 API입니다")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/request")
public class RequestController {
    private final RequestService requestService;

    @Operation(summary = "매칭 추가")
    @PostMapping("/add")
    public ApiResponse<String> createRequest(@AuthUser Long memberId, @Valid @RequestBody RequestDTO.NewRequestDTO newRequest) {

        log.info("매칭 추가 : {}", memberId);

        requestService.createNewRequest(memberId, newRequest);

        return ApiResponse.onSuccess("등록 성공");
    }

    @Operation(summary = "매칭에 참가 신청")
    @PostMapping("/joinRequest")
    public ApiResponse<RequestDTO.JoinRequestDTO> joinRequest(@AuthUser Long memberId,@RequestBody RequestDTO.JoinRequestDTO joinRequest) {

        log.info("매칭에 참가 신청 {}", memberId);

        RequestDTO.JoinRequestDTO responseDTO = requestService.createJoinRequest(memberId ,joinRequest);

        return ApiResponse.onSuccess(responseDTO);
    }

    @Operation(summary = "매칭 삭제")
    @DeleteMapping("/{requestId}")
    public ApiResponse<String> deleteRequest(@PathVariable Long requestId, @AuthUser Long memberId) {
        requestService.deleteRequest(requestId, memberId);
        return ApiResponse.onSuccess("삭제 성공");
    }

    @Operation(summary = "매칭에 좋아요 누르기")
    @PostMapping("/like/{requestId}")
    public ApiResponse<RequestDTO.LikeRequestDTO> likeRequest(@AuthUser Long memberId,@PathVariable Long requestId) {
        RequestDTO.LikeRequestDTO responseDTO = requestService.likeRequest(memberId, requestId);
        return ApiResponse.onSuccess(responseDTO);
    }

    @Operation(summary = "매칭 좋아요 취소")
    @DeleteMapping("/like/{requestId}")
    public ApiResponse<String> deleteLikeRequest(@AuthUser Long memberId, @PathVariable Long requestId) {
        requestService.deleteLikeRequest(memberId, requestId);
        return ApiResponse.onSuccess("삭제 성공");
    }

    @Operation(summary = "매칭 신청 승낙하기" )
    @PatchMapping("/accept/{requestId}")
    public ApiResponse<RequestDTO.isAcceptedDTO> acceptRequest(@AuthUser Long memberId, @PathVariable Long requestId, @RequestParam Boolean isAccepted) {

        log.info("매칭 신청 승낙하기 {}", requestId);

        RequestDTO.isAcceptedDTO responseDTO = requestService.acceptRequest(memberId, requestId, isAccepted);

        return ApiResponse.onSuccess(responseDTO);
    }
}
