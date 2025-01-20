package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.MatchRequestListDto;
import com.umc.meetpick.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matches")  // URL 경로 설정
public class MatchController {

    private final RequestService requestService;

    @GetMapping  // GET /api/matches
    public ApiResponse<MatchRequestListDto> getMatchRequests(
            @RequestParam Long memberId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {

        MatchRequestListDto response = requestService.getMatchRequests(memberId, pageable);


        return ApiResponse.onSuccess(response);
    }
}