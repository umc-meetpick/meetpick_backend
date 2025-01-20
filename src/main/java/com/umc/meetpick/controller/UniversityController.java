package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.entity.University;
import com.umc.meetpick.service.UniversityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "대학교 관련 API", description = "대학교 관련 API입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/university")
public class UniversityController {

    private final UniversityService universityService;

    @Operation(summary = "대학교 이름으로 조회 API")
    @GetMapping("/list/{universityName}")
    public ApiResponse<List<University>> getUniversityByName(@PathVariable("universityName") String universityName) {
        return ApiResponse.onSuccess(universityService.getUniversityList(universityName));
    }

}
