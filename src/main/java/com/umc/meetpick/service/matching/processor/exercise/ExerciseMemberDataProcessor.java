package com.umc.meetpick.service.matching.processor.exercise;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;

import com.umc.meetpick.entity.matchingdata.exercise.MemberDataExercise;
import com.umc.meetpick.enums.ExerciseType;
import com.umc.meetpick.enums.Gender;

import com.umc.meetpick.repository.exercise.ExerciseMemberDataRepository;
import com.umc.meetpick.service.matching.processor.MemberDataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class ExerciseMemberDataProcessor implements MemberDataProcessor {

    private final ExerciseMemberDataRepository exerciseMemberDataRepository;

    public void process(MemberSecondProfile memberSecondProfile){
        Member member = memberSecondProfile.getMember();
        MemberProfile memberProfile = member.getMemberProfile();

        MemberDataExercise memberData = MemberDataExercise.builder().build();

        // 기본 정보 설정
        setGender(memberData, member.getGender());
        setAge(memberData, member.getAge());
        setMbti(memberData, memberProfile.getMBTI().toString());
        setMajor(memberData, memberProfile.getSubMajor().getMajor().getName());

        // 운동 관련 정보
        setExerciseType(memberData, memberSecondProfile.getExerciseType());
        setIsSchool(memberData, memberSecondProfile.getIsSchool());

        memberData.setMember(member);
        memberData.setUniversity(member.getUniversity());

        exerciseMemberDataRepository.save(memberData);
    }

    // setGender
    private void setGender(MemberDataExercise memberData, Gender gender){
        switch (gender) {
            case MALE -> memberData.setGender(0.0);
            case FEMALE -> memberData.setGender(3.0);
            case ALL -> memberData.setGender(1.5);
        }
    }

    // setAge
    private void setAge(MemberDataExercise memberData, Integer Age){
        memberData.setAge((double) (Age));
    }

    // setMbti
    private void setMbti(MemberDataExercise memberData, String mbti){
        if(mbti.charAt(0) == 'I'){
            memberData.setIE(0.5);
        }

        if(mbti.charAt(1) == 'S'){
            memberData.setSN(0.5);
        }

        if(mbti.charAt(2) == 'T'){
            memberData.setTF(0.5);
        }

        if(mbti.charAt(3) == 'J'){
            memberData.setJP(0.5);
        }
    }

    // setMajor
    private void setMajor(MemberDataExercise memberData, String majorName){
        switch (majorName) {
            case "공학 계열" -> memberData.setEngineering(0.5);
            case "자연과학 계열" -> memberData.setScience(0.5);
            case "인문학 계열" -> memberData.setHumanities(0.5);
            case "사회과학 계열" -> memberData.setSocialScience(0.5);
            case "의학 계열" -> memberData.setMedicine(0.5);
            case "예술·체육 계열" -> memberData.setArtsAndPhysical(0.5);
            case "농·생명 계열" -> memberData.setAgricultureAndLife(0.5);
            case "융합/특성화 계열" -> memberData.setConvergenceAndSpecialization(0.5);
        }
    }

    // 운동 도메인 -setExerciseType
    private void setExerciseType(MemberDataExercise memberData, ExerciseType exerciseType){
        switch (exerciseType) {
            case BOWLING -> memberData.setBowling(3.0);
            case CLIMBING -> memberData.setClimbing(3.0);
            case TABLE_TENNIS -> memberData.setTableTennis(4.0);
            case FITNESS -> memberData.setFitness(2.5);
            case RUNNING -> memberData.setRunning(2.5);
            case SOCCER -> memberData.setSoccer(4.0);
            case BASKETBALL -> memberData.setBasketball(4.0);
            case TENNIS_BADMINTON -> memberData.setTennisBadminton(3.0);
        }
    }

    private void setIsSchool(MemberDataExercise memberData, Boolean isSchool){
        memberData.setIsSchool(isSchool ? 2.0 : 0.0);
    }
}