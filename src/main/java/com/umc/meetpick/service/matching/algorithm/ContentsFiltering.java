package com.umc.meetpick.service.matching.algorithm;

import com.nimbusds.jose.Algorithm;
import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.RecommendDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberDistance;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.entity.matchingdata.food.MemberData;
import com.umc.meetpick.entity.matchingdata.food.MemberRequestData;
import com.umc.meetpick.repository.MemberDataRepository;
import com.umc.meetpick.repository.MemberRequestDataRepository;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import com.umc.meetpick.service.matching.factory.MatchingDtoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContentsFiltering implements MatchingAlgorithm {

    private final MemberDataRepository memberDataRepository;
    private final MemberRequestDataRepository memberRequestDataRepository;
    private final MemberMappingRepository memberMappingRepository;

    @Override
    public RecommendDto.FoodRecommendPageDto recommend(Member member) {

        MemberRequestData memberRequestData = memberRequestDataRepository.findByMember(member)
                .orElseThrow(() -> new GeneralHandler(ErrorCode.MEMBER_DATA_NOT_PRESENT));

        // 같은 대학만 추출
        List<MemberData> memberDataList = memberDataRepository.findAllByUniversity(member.getUniversity());

        // 본인과 매칭 완료된 MemberData 제거
        memberDataList = memberDataList.stream()
                .filter(data -> !data.getMember().equals(member))  // 본인 제거
                .filter(data -> !isMatchingCompleted(member, data.getMember()))  // 매칭 완료된 경우 제거
                .toList();

        // 결과를 저장할 리스트
        List<MemberDistance> memberDistances = new ArrayList<>();

        for (MemberData data : memberDataList) {
            double distance = calculateDistance(memberRequestData, data);
            memberDistances.add(new MemberDistance(data, distance));
        }

        // 거리 순으로 정렬
        memberDistances.sort(Comparator.comparingDouble(MemberDistance::getDistance));

        // 상위 5개 선택
        List<MemberData> top5MemberData = memberDistances.stream()
                .limit(5)
                .map(MemberDistance::getMemberData)  // MemberDistance에서 MemberData 추출
                .collect(Collectors.toList());

        return MatchingDtoFactory.memberSecondProfileToFoodRecommendtDto(top5MemberData);
    }

    private double calculateDistance(MemberRequestData requestData, MemberData data) {
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
        sum += Math.pow(requestData.getKOREAN() - data.getKOREAN(), 2);
        sum += Math.pow(requestData.getWESTERN() - data.getWESTERN(), 2);
        sum += Math.pow(requestData.getJAPANESE() - data.getJAPANESE(), 2);
        sum += Math.pow(requestData.getCHINESE() - data.getCHINESE(), 2);
        sum += Math.pow(requestData.getVIETNAMESE() - data.getVIETNAMESE(), 2);

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


