package com.umc.meetpick.service.matching.algorithm;


import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.RecommendDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.matchingdata.MemberDistance;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.entity.matchingdata.exercise.MemberDataExercise;
import com.umc.meetpick.entity.matchingdata.exercise.MemberRequestDataExercise;
import com.umc.meetpick.repository.exercise.ExerciseMemberDataRepository;
import com.umc.meetpick.repository.exercise.ExerciseMemberRequestDataRepository;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import com.umc.meetpick.service.matching.factory.MatchingDtoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("운동")
@RequiredArgsConstructor
public class ExerciseContentsFiltering implements MatchingAlgorithm<RecommendDto.ExerciseRecommendPageDto> {

    private final ExerciseMemberDataRepository exerciseMemberDataRepository;
    private final ExerciseMemberRequestDataRepository exerciseMemberRequestDataRepository;
    private final MemberMappingRepository memberMappingRepository;

    @Override
    public RecommendDto.ExerciseRecommendPageDto recommend(Member member) {

        MemberRequestDataExercise memberRequestData = exerciseMemberRequestDataRepository.findByMember(member)
                .orElseThrow(() -> new GeneralHandler(ErrorCode.MEMBER_DATA_NOT_PRESENT));

        // 같은 대학만 추출
        List<MemberDataExercise> memberDataList = exerciseMemberDataRepository.findAllByUniversity(member.getUniversity());

        // 본인과 매칭 완료된 MemberData 제거
        memberDataList = memberDataList.stream()
                .filter(data -> !data.getMember().equals(member))  // 본인 제거
                .filter(data -> !isMatchingCompleted(member, data.getMember()))  // 매칭 완료된 경우 제거
                .toList();

        // 결과를 저장할 리스트
        List<MemberDistance<MemberDataExercise>> memberDistances = new ArrayList<>();

        for (MemberDataExercise data : memberDataList) {
            double distance = calculateDistance(memberRequestData, data);
            memberDistances.add(new MemberDistance<>(data, distance));
        }

        // 거리 순으로 정렬
        memberDistances.sort(Comparator.comparingDouble(MemberDistance::getDistance));

        // 상위 5개 선택
        List<MemberDataExercise> top5MemberData = memberDistances.stream()
                .limit(5)
                .map(MemberDistance::getMemberData)//제네릭을 활용한 타입 추론
                //.map(memberDistance -> (MemberDataExercise) memberDistance.getMemberData())
                .collect(Collectors.toList());

        return MatchingDtoFactory.memberSecondProfileToExerciseRecommendDto(top5MemberData);
    }

    private double calculateDistance(MemberRequestDataExercise requestData, MemberDataExercise data) {
        double sum = 0.0;
        sum += Math.pow(requestData.getGender() - data.getGender(), 2);
        sum += Math.pow(requestData.getAge() - data.getAge(), 2);
        sum += Math.pow(requestData.getIE() - data.getIE(), 2);
        sum += Math.pow(requestData.getSN() - data.getSN(), 2);
        sum += Math.pow(requestData.getJP() - data.getJP(), 2);
        sum += Math.pow(requestData.getEngineering() - data.getEngineering(), 2);
        sum += Math.pow(requestData.getScience() - data.getScience(), 2);
        sum += Math.pow(requestData.getHumanities() - data.getHumanities(), 2);
        sum += Math.pow(requestData.getSocialScience() - data.getSocialScience(), 2);
        sum += Math.pow(requestData.getMedicine() - data.getMedicine(), 2);
        sum += Math.pow(requestData.getArtsAndPhysical() - data.getArtsAndPhysical(), 2);
        sum += Math.pow(requestData.getAgricultureAndLife() - data.getAgricultureAndLife(), 2);
        sum += Math.pow(requestData.getConvergenceAndSpecialization() - data.getConvergenceAndSpecialization(), 2);
        sum += Math.pow(requestData.getBowling() - data.getBowling(), 2);
        sum += Math.pow(requestData.getClimbing() - data.getClimbing(), 2);
        sum += Math.pow(requestData.getTableTennis() - data.getTableTennis(), 2);
        sum += Math.pow(requestData.getFitness() - data.getFitness(), 2);
        sum += Math.pow(requestData.getRunning() - data.getRunning(), 2);
        sum += Math.pow(requestData.getSoccer() - data.getSoccer(), 2);
        sum += Math.pow(requestData.getBasketball() - data.getBasketball(), 2);
        sum += Math.pow(requestData.getTennisBadminton() - data.getTennisBadminton(), 2);
        sum += Math.pow(requestData.getIsSchool() - data.getIsSchool(), 2);

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