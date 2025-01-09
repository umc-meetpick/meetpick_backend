package com.umc.meetpick.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "체크리스트 관련 API", description = "체크리스트 관련 API입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TempController {
    @GetMapping("/example")
    public String health() {
        return "예시입니다";
    }
}
