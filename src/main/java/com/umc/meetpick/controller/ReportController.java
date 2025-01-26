package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.ReportDTO;
import com.umc.meetpick.service.report.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 신고 관련 controller
@Tag(name = "신고 관련 API", description = "신고 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;

    @Operation(summary = "신고 추가")
    @PostMapping("")
    public ApiResponse<ReportDTO.PostReportDTO> postReport(@RequestBody ReportDTO.PostReportDTO postReport) {
        ReportDTO.PostReportDTO responseDTO = reportService.postReport(postReport);
        return ApiResponse.onSuccess(responseDTO);
    }
}
