package com.umc.meetpick;

import com.umc.meetpick.domain.factory.MemberFactory;
import com.umc.meetpick.domain.factory.MemberSecondProfileFactory;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import com.umc.meetpick.service.matching.MatchingService;
import com.umc.meetpick.service.member.MemberService;
import com.umc.meetpick.service.home.HomeService;
import com.umc.meetpick.service.report.ReportService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
public class TestAPI {

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMappingRepository memberMappingRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private HomeService homeService;

    @Autowired
    private MemberSecondProfileRepository memberSecondProfileRepository;


    /*@Test
    void testAPI(){

        System.out.println(memberMappingRepository.findAll().get(0).getMember().getId());
        System.out.println(memberMappingRepository.findAll().get(0).getMemberSecondProfile().getMember().getId());

        Pageable pageable = Pageable.ofSize(10);

        System.out.println(matchingService.getMatchRequests(7L, pageable).getTotalPages());

    }*/

    /*@Test
    void testAPI2(){

        System.out.println(reportService.postReport(ReportDTO.PostReportDTO.builder()
                .reportedId(5L)
                .reporterId(7L)
                .content("hahaha")
                .reportType("욕설")
                .build()));

    }*/

}
