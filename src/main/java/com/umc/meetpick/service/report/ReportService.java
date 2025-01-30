package com.umc.meetpick.service.report;

import com.umc.meetpick.dto.ReportDTO;

public interface ReportService {
    ReportDTO.PostReportDTO postReport(ReportDTO.PostReportDTO newReport);

}
