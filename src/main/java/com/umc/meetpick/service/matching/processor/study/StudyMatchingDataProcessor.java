package com.umc.meetpick.service.matching.processor.study;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileSubMajor;
import com.umc.meetpick.entity.matchingdata.exercise.MemberRequestDataExercise;
import com.umc.meetpick.entity.matchingdata.study.MemberRequestDataStudy;
import com.umc.meetpick.enums.StudyType;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.repository.study.StudyMemberRequestDataRepository;
import com.umc.meetpick.service.matching.processor.MatchingDataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
public class StudyMatchingDataProcessor implements MatchingDataProcessor {

    private final StudyMemberRequestDataRepository studyMemberRequestDataRepository;

    public void process(MemberSecondProfile memberSecondProfile){
        Member member = memberSecondProfile.getMember();

        MemberRequestDataStudy memberRequestData = MemberRequestDataStudy.builder().build();

        // 기본 정보 설정 (기존과 동일)
        setGender(memberRequestData, memberSecondProfile.getGender());
        setAge(memberRequestData, memberSecondProfile.getMinAge(), memberSecondProfile.getMaxAge());
        setMbti(memberRequestData, memberSecondProfile.getMbti());
        if(memberSecondProfile.getMemberSecondProfileMajorList() != null){
            setMajor(memberRequestData, memberSecondProfile.getMemberSecondProfileMajorList());
        }

        // 스터디 관련 정보 설정 (스터디 도메인 특화)
        setStudyType(memberRequestData, memberSecondProfile.getStudyType());
        setIsOnline(memberRequestData, memberSecondProfile.getIsOnline());
        setStudyTimes(memberRequestData, memberSecondProfile.getStudyTimes());

        memberRequestData.setMember(memberSecondProfile.getMember());
        memberRequestData.setUniversity(member.getUniversity());

        studyMemberRequestDataRepository.save(memberRequestData);
    }


    // setGender
    private void setGender(MemberRequestDataStudy memberRequestData, Gender gender){
        switch (gender) {
            case MALE -> memberRequestData.setGender(0.0);
            case FEMALE -> memberRequestData.setGender(3.0);
            case ALL -> memberRequestData.setGender(1.5);
        };
    }

    // setAge
    private void setAge(MemberRequestDataStudy memberRequestData, Integer minAge, Integer maxAge){
        memberRequestData.setAge((double) ((maxAge + minAge) / 2));
    }

    // setMbti
    private void setMbti(MemberRequestDataStudy memberRequestData, String mbti){
        if(mbti.charAt(0) == 'I'){
            memberRequestData.setIE(0.5);
        }

        if(mbti.charAt(1) == 'S'){
            memberRequestData.setSN(0.5);
        }

        if(mbti.charAt(2) == 'T'){
            memberRequestData.setTF(0.5);
        }

        if(mbti.charAt(3) == 'J'){
            memberRequestData.setJP(0.5);
        }
    }

    // setMajor
    private void setMajor(MemberRequestDataStudy memberRequestData, List<MemberSecondProfileSubMajor> secondProfileSubMajors){
        secondProfileSubMajors.forEach(
                secondProfileSubMajor -> {
                    switch (secondProfileSubMajor.getSubMajor().getMajor().getName()){
                        case "공학 계열" : memberRequestData.setEngineering(1.0);
                        case "자연과학 계열" : memberRequestData.setScience(1.0);
                        case "인문학 계열" : memberRequestData.setHumanities(1.0);
                        case "사회과학 계열" : memberRequestData.setSocialScience(1.0);
                        case "의학 계열" : memberRequestData.setMedicine(1.0);
                        case "예술·체육 계열" : memberRequestData.setArtsAndPhysical(1.0);
                        case "농·생명 계열" : memberRequestData.setAgricultureAndLife(1.0);
                        case "융합/특성화 계열" : memberRequestData.setConvergenceAndSpecialization(1.0);
                    }
                }
        );
    }

    // 스터디 도메인 -setStudyType
    private void setStudyType(MemberRequestDataStudy memberRequestData, StudyType studyType){
        switch (studyType) {
            case MAJOR -> memberRequestData.setMajor(2.5);
            case NON_MAJOR -> memberRequestData.setNonMajor(2.0);
            case STUDY -> memberRequestData.setStudy(2.0);
        }
    }

    private void setIsOnline(MemberRequestDataStudy memberRequestData, Boolean isOnline){
        memberRequestData.setIsOnline(isOnline ? 2.0 : 0.0);
    }

    private void setStudyTimes(MemberRequestDataStudy memberRequestData, int studyTimes){
        memberRequestData.setStudyTimes((double) studyTimes / 5);
    }
}