package com.umc.meetpick.service.report;

import com.umc.meetpick.dto.ReportDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.Report;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.ReportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.umc.meetpick.enums.ReportType.fromKoreanName;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;

    @Override
    public ReportDTO.PostReportDTO postReport(Long reporterId,ReportDTO.PostReportDTO newReport) {

        Member reportedMember = memberRepository.findById(newReport.getReportedId())
                .orElseThrow(() -> new EntityNotFoundException("신고 대상자 아이디 에러"));

        Member reporterMember = memberRepository.findById(reporterId)
                .orElseThrow(() -> new EntityNotFoundException("신고자 아이디 에러"));

        Report report = Report.builder()
                .writer(reporterMember)
                .matchingMember(reportedMember)
                .content(newReport.getContent())
                .reportType(fromKoreanName(newReport.getReportType()))
                .build();

        Report savedReport = reportRepository.save(report);

        return ReportDTO.PostReportDTO.builder()
                .reportedId(savedReport.getMatchingMember().getId())
                //.reporterId(savedReport.getWriter().getId())
                .content(savedReport.getContent())
                .reportType(savedReport.getReportType().getKoreanName())
                .build();
    }
}
