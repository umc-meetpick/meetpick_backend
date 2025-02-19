package com.umc.meetpick.service.matching.algorithm;


import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.RecommendDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.matchingdata.MemberDistance;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.entity.matchingdata.study.MemberDataStudy;
import com.umc.meetpick.entity.matchingdata.study.MemberRequestDataStudy;
import com.umc.meetpick.repository.study.StudyMemberDataRepository;
import com.umc.meetpick.repository.study.StudyMemberRequestDataRepository;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import com.umc.meetpick.service.matching.factory.MatchingDtoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("공부")
@RequiredArgsConstructor
public class StudyContentsFiltering implements MatchingAlgorithm<RecommendDto.StudyRecommendPageDto> {

    private final StudyMemberDataRepository studyMemberDataRepository;
    private final StudyMemberRequestDataRepository studyMemberRequestDataRepository;
    private final MemberMappingRepository memberMappingRepository;

    @Override
    public RecommendDto.StudyRecommendPageDto recommend(Member member) {
        MemberRequestDataStudy memberRequestData = studyMemberRequestDataRepository.findByMember(member)
                .orElseThrow(() -> new GeneralHandler(ErrorCode.MEMBER_DATA_NOT_PRESENT));

        // 같은 대학만 추출
        List<MemberDataStudy> memberDataList = studyMemberDataRepository.findAllByUniversity(member.getUniversity());

        // 본인과 매칭 완료된 MemberData 제거
        memberDataList = memberDataList.stream()
                .filter(data -> !data.getMember().equals(member))
                .filter(data -> !isMatchingCompleted(member, data.getMember()))
                .toList();

        List<MemberDistance<MemberDataStudy>> memberDistances = new ArrayList<>();

        for (MemberDataStudy data : memberDataList) {
            double distance = calculateDistance(memberRequestData, data);
            memberDistances.add(new MemberDistance<>(data, distance));
        }

        memberDistances.sort(Comparator.comparingDouble(MemberDistance::getDistance));

        List<MemberDataStudy> top5MemberData = memberDistances.stream()
                .limit(5)
                .map(MemberDistance::getMemberData) //타입 추론
                //.map(memberDistance -> (MemberDataStudy) memberDistance.getMemberData())
                .collect(Collectors.toList());

        return MatchingDtoFactory.memberSecondProfileToStudyRecommendDto(top5MemberData);
    }

    private double calculateDistance(MemberRequestDataStudy requestData, MemberDataStudy data) {
        double sum = 0.0;
        sum += Math.pow(requestData.getGender() - data.getGender(), 2);
        sum += Math.pow(requestData.getAge() - data.getAge(), 2);
        sum += Math.pow(requestData.getIE() - data.getIE(), 2);
        sum += Math.pow(requestData.getSN() - data.getSN(), 2);
        sum += Math.pow(requestData.getTF() - data.getTF(), 2);
        sum += Math.pow(requestData.getJP() - data.getJP(), 2);
        sum += Math.pow(requestData.getEngineering() - data.getEngineering(), 2);
        sum += Math.pow(requestData.getScience() - data.getScience(), 2);
        sum += Math.pow(requestData.getHumanities() - data.getHumanities(), 2);
        sum += Math.pow(requestData.getSocialScience() - data.getSocialScience(), 2);
        sum += Math.pow(requestData.getMedicine() - data.getMedicine(), 2);
        sum += Math.pow(requestData.getArtsAndPhysical() - data.getArtsAndPhysical(), 2);
        sum += Math.pow(requestData.getAgricultureAndLife() - data.getAgricultureAndLife(), 2);
        sum += Math.pow(requestData.getConvergenceAndSpecialization() - data.getConvergenceAndSpecialization(), 2);
        sum += Math.pow(requestData.getMajor() - data.getMajor(), 2);
        sum += Math.pow(requestData.getNonMajor() - data.getNonMajor(), 2);
        sum += Math.pow(requestData.getStudy() - data.getStudy(), 2);
        sum += Math.pow(requestData.getIsOnline() - data.getIsOnline(), 2);
        sum += Math.pow(requestData.getStudyTimes() - data.getStudyTimes(), 2);

        return Math.sqrt(sum);
    }

    private boolean isMatchingCompleted(Member member1, Member member2) {
        List<MemberSecondProfileMapping> mappings = memberMappingRepository.findAllByMemberSecondProfile_Member(member1);

        for (MemberSecondProfileMapping mapping : mappings) {
            if (mapping.getMember().equals(member2) && mapping.getStatus()) {
                return true;
            }
        }
        return false;
    }
}