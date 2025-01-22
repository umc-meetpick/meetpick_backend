package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.entity.Request;
import com.umc.meetpick.service.request.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// 새로운 매칭 요청 만드는 controller
@Tag(name = "새로운 매칭 요청 관련 API", description = "새로운 매칭 요청 관련 API입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/request")
public class RequestController {
    private final RequestService requestService;

    @Operation(summary = "매칭 요청 추가")
    @PostMapping("/add")
    public ApiResponse<RequestDTO.NewRequestDTO> createRequest(@RequestBody RequestDTO.NewRequestDTO newRequest) {
        RequestDTO.NewRequestDTO responseDTO = requestService.createNewRequest(newRequest);
        return ApiResponse.onSuccess(responseDTO);
    }

    @Operation(summary = "매칭에 참가 신청")
    @PostMapping("/joinRequest")
    public ApiResponse<RequestDTO.JoinRequestDTO> joinRequest(@RequestBody RequestDTO.JoinRequestDTO joinRequest) {
        RequestDTO.JoinRequestDTO responseDTO = requestService.createJoinRequest(joinRequest);
        return ApiResponse.onSuccess(responseDTO);
    }

    @Operation(summary = "매칭 삭제")
    @DeleteMapping("/{requestId}")
    public ApiResponse<String> deleteRequest(@PathVariable Long requestId, @RequestParam Long userId) {
        requestService.deleteRequest(requestId, userId);
        return ApiResponse.onSuccess("삭제 성공");
    }


}
