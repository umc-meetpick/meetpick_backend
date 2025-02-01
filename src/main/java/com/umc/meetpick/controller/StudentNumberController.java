package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.StudentNumberDTO;
import com.umc.meetpick.service.StudentNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "학번 API", description = "학번 설정 관련 API입니다")
@RestController
@RequestMapping("/api/student-number")
@RequiredArgsConstructor
@Slf4j
public class StudentNumberController {

    private final StudentNumberService studentNumberService;

    @Operation(summary = "학번 설정")
    @PostMapping("/set")
    public ApiResponse<StudentNumberDTO.StudentNumberResponseDTO> setStudentNumber(
            @RequestParam("memberId") Long memberId,
            @Valid @RequestBody StudentNumberDTO.StudentNumberRequestDTO requestDTO) {
        log.info("학번 설정 요청 - memberId={}, studentNumber={}", memberId, requestDTO.getStudentNumber());
        return studentNumberService.setStudentNumber(memberId, requestDTO);
    }
}
