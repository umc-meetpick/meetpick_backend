package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.entity.Request;
import com.umc.meetpick.service.request.RequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 새로운 매칭 요청 만드는 controller
@Tag(name = "새로운 매칭 요청 관련 API", description = "새로운 매칭 요청 관련 API입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/request")
public class RequestController {
    private final RequestService requestService;

    @PostMapping("/add")
    public ApiResponse<RequestDTO.NewRequestDTO> createRequest(@RequestBody RequestDTO.NewRequestDTO newRequest) {
        Request createdRequest = requestService.createNewRequest(newRequest);
        // Request -> NewRequestDTO로 변환
        RequestDTO.NewRequestDTO responseDTO = RequestDTO.NewRequestDTO.builder()
                .writerId(createdRequest.getWriter().getId())
                //.majorName(createdRequest.getMajor().getName())
                .subMajorName(createdRequest.getSubMajor().getName())
                .hobbyName(createdRequest.getHobby().getName())
                .studentNumber(createdRequest.getStudentNumber())
                .mbti(createdRequest.getMbti())
                .minAge(createdRequest.getMinAge())
                .maxAge(createdRequest.getMaxAge())
                .minTime(createdRequest.getMinTime())
                .maxTime(createdRequest.getMaxTime())
                .food(createdRequest.getFood())
                .maxPeople(createdRequest.getMaxPeople())
                .type(createdRequest.getType())
                .build();

        return ApiResponse.onSuccess(responseDTO);
    }

}
