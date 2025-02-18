// ExerciseMatchingDataProcessor.java
package com.umc.meetpick.service.matching.processor.exercise;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileSubMajor;
import com.umc.meetpick.entity.matchingdata.exercise.MemberRequestDataExercise;
import com.umc.meetpick.enums.ExerciseType;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.repository.exercise.ExerciseMemberRequestDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseMatchingDataProcessor {

    private final ExerciseMemberRequestDataRepository exerciseMemberRequestDataRepository;

    public void process(MemberSecondProfile memberSecondProfile){
        Member member = memberSecondProfile.getMember();

        MemberRequestDataExercise memberRequestData = MemberRequestDataExercise.builder().build();
        setGender(memberRequestData, memberSecondProfile.getGender());
        setAge(memberRequestData, memberSecondProfile.getMinAge(), memberSecondProfile.getMaxAge());
        setMbti(memberRequestData, memberSecondProfile.getMbti());
        if(memberSecondProfile.getMemberSecondProfileMajorList() != null){
            setMajor(memberRequestData, memberSecondProfile.getMemberSecondProfileMajorList());
        }
        // 운동 관련 정보
        setExerciseType(memberRequestData, memberSecondProfile.getExerciseType());
        setIsSchool(memberRequestData, memberSecondProfile.getIsSchool());
        memberRequestData.setMember(memberSecondProfile.getMember());
        memberRequestData.setUniversity(member.getUniversity());

        exerciseMemberRequestDataRepository.save(memberRequestData);
    }

    // setGender
    private void setGender(MemberRequestDataExercise memberRequestData, Gender gender){
        switch (gender) {
            case MALE -> memberRequestData.setGender(0.0);
            case FEMALE -> memberRequestData.setGender(3.0);
            case ALL -> memberRequestData.setGender(1.5);
        };
    }

    // setAge
    private void setAge(MemberRequestDataExercise memberRequestData, Integer minAge, Integer maxAge){
        memberRequestData.setAge((double) ((maxAge + minAge) / 2));
    }

    // setMbti
    private void setMbti(MemberRequestDataExercise memberRequestData, String mbti){
        if(mbti.charAt(0) == 'I'){
            memberRequestData.setIE(0.8);
        }

        if(mbti.charAt(1) == 'S'){
            memberRequestData.setSN(0.8);
        }

        if(mbti.charAt(2) == 'T'){
            memberRequestData.setTF(0.8);
        }

        if(mbti.charAt(3) == 'J'){
            memberRequestData.setJP(0.8);
        }
    }

    // setMajor
    private void setMajor(MemberRequestDataExercise memberRequestData, List<MemberSecondProfileSubMajor> secondProfileSubMajors){
        secondProfileSubMajors.forEach(
                secondProfileSubMajor -> {
                    switch (secondProfileSubMajor.getSubMajor().getMajor().getName()){
                        case "공학 계열" : memberRequestData.setEngineering(0.5);
                        case "자연과학 계열" : memberRequestData.setScience(0.5);
                        case "인문학 계열" : memberRequestData.setHumanities(0.5);
                        case "사회과학 계열" : memberRequestData.setSocialScience(0.5);
                        case "의학 계열" : memberRequestData.setMedicine(0.5);
                        case "예술·체육 계열" : memberRequestData.setArtsAndPhysical(0.5);
                        case "농·생명 계열" : memberRequestData.setAgricultureAndLife(0.5);
                        case "융합/특성화 계열" : memberRequestData.setConvergenceAndSpecialization(0.5);
                    }
                }
        );
    }

    // 운동 도메인 -setExerciseType
    private void setExerciseType(MemberRequestDataExercise memberRequestData, ExerciseType exerciseType){
        switch (exerciseType) {
            case BOWLING -> memberRequestData.setBowling(2.0);
            case CLIMBING -> memberRequestData.setClimbing(2.0);
            case TABLE_TENNIS -> memberRequestData.setTableTennis(2.0);
            case FITNESS -> memberRequestData.setFitness(2.0);
            case RUNNING -> memberRequestData.setRunning(2.0);
            case SOCCER -> memberRequestData.setSoccer(2.0);
            case BASKETBALL -> memberRequestData.setBasketball(2.0);
            case TENNIS_BADMINTON -> memberRequestData.setTennisBadminton(2.0);
        }
    }

    private void setIsSchool(MemberRequestDataExercise memberRequestData, Boolean isSchool){
        memberRequestData.setIsSchool(isSchool ? 2.0 : 0.0);
    }
}