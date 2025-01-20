package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버 관련 API", description = "멤버 관련 API입니다")
@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "랜덤 유저 반환 API")
    @GetMapping("/random-user")
    public ApiResponse<MemberResponseDTO> getRandomMember() {
        return ApiResponse.onSuccess(memberService.getRandomMember());
    }
}
