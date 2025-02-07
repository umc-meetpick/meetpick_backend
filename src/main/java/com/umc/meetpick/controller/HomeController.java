package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.service.home.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Tag(name = "대학교 관련 API", description = "대학교 관련 API입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/university")
@Slf4j
public class HomeController {

    private final HomeService homeService;

    @Operation(summary = "대학교 이름으로 조회 API")
    @GetMapping("/list/{universityName}")
    public ApiResponse<List<Map<String, String>>> getUniversityByName(@PathVariable("universityName") String universityName) {

        log.info("GET - 대학교 이름으로 조회 : {}", universityName);

        return ApiResponse.onSuccess(homeService.getUniversityList(universityName));
    }

    // 작동 됨. 단, member Id가 어떻게 저장되냐에 따라 다르다
    @Operation(summary = "랜덤 유저 반환 API")
    @GetMapping("/random-user")
    public ApiResponse<MemberResponseDTO> getRandomMember(@PathParam("MateType") String mateType) {

        log.info("GET - 랜덤 유저 반환 : {}", mateType);

        return ApiResponse.onSuccess(homeService.getRandomMember(mateType));
    }

}
