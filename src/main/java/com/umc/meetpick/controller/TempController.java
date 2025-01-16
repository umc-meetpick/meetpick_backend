package com.umc.meetpick.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "대학교 관련 API", description = "대학교 관련 API입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/university")
public class TempController {

    @Operation(summary = "대학교 리스트 반환 API")
    @GetMapping("/list")
    public String health() {
        return "예시입니다";
    }

}
