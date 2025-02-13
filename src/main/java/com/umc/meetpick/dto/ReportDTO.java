package com.umc.meetpick.dto;

import com.umc.meetpick.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportDTO {

    // 신고 추가
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostReportDTO {
        private Long reportedId; // 신고 당한 사람
        //private Long reporterId; // 신고 한 사람
        private String content;
        private String reportType;
    }
}
