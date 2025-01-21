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
        RequestDTO.NewRequestDTO responseDTO = requestService.createNewRequest(newRequest);
        return ApiResponse.onSuccess(responseDTO);
    }

    @PostMapping("/joinRequest")
    public ApiResponse<RequestDTO.JoinRequestDTO> joinRequest(@RequestBody RequestDTO.JoinRequestDTO joinRequest) {
        //RequestDTO.JoinRequestDTO responseDTO = requestService.
    }

}
