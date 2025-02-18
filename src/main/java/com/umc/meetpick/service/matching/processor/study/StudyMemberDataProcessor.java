package com.umc.meetpick.service.matching.processor.study;



import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.matchingdata.study.MemberDataStudy;
import com.umc.meetpick.enums.StudyType;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.repository.study.StudyMemberDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class StudyMemberDataProcessor {

    private final StudyMemberDataRepository studyMemberDataRepository;

    public void process(MemberSecondProfile memberSecondProfile){
        Member member = memberSecondProfile.getMember();
        MemberProfile memberProfile = member.getMemberProfile();

        MemberDataStudy memberData = MemberDataStudy.builder().build();

        // 기본 정보 설정 (기존과 동일)
        setGender(memberData, member.getGender());
        setAge(memberData, member.getAge());
        setMbti(memberData, memberProfile.getMBTI().toString());
        setMajor(memberData, memberProfile.getSubMajor().getMajor().getName());

        // 스터디 관련 정보 설정 (스터디 도메인 특화)
        setStudyType(memberData, memberSecondProfile.getStudyType());
        setIsOnline(memberData, memberSecondProfile.getIsOnline());
        setStudyTimes(memberData, memberSecondProfile.getStudyTimes());

        memberData.setMember(member);
        memberData.setUniversity(member.getUniversity());

        studyMemberDataRepository.save(memberData);
    }



      private void setGender(MemberDataStudy memberData, Gender gender){
        switch (gender) {
            case MALE -> memberData.setGender(0.0);
            case FEMALE -> memberData.setGender(3.0);
            case ALL -> memberData.setGender(1.5);
        };
    }

    private void setAge(MemberDataStudy memberData, Integer Age){
        memberData.setAge((double) (Age));
    }

    private void setMbti(MemberDataStudy memberData, String mbti){
        if(mbti.charAt(0) == 'I'){
            memberData.setIE(0.0);
        }

        if(mbti.charAt(1) == 'S'){
            memberData.setSN(0.0);
        }

        if(mbti.charAt(2) == 'T'){
            memberData.setTF(0.0);
        }

        if(mbti.charAt(3) == 'J'){
            memberData.setJP(0.0);
        }
    }

    private void setMajor(MemberDataStudy memberData, String majorName){
        switch (majorName) {
            case "공학 계열" -> memberData.setEngineering(1.0);
            case "자연과학 계열" -> memberData.setScience(1.0);
            case "인문학 계열" -> memberData.setHumanities(1.0);
            case "사회과학 계열" -> memberData.setSocialScience(1.0);
            case "의학 계열" -> memberData.setMedicine(1.0);
            case "예술·체육 계열" -> memberData.setArtsAndPhysical(1.0);
            case "농·생명 계열" -> memberData.setAgricultureAndLife(1.0);
            case "융합/특성화 계열" -> memberData.setConvergenceAndSpecialization(1.0);
        }
    }


    // 운동 도메인 -setStudyType
    private void setStudyType(MemberDataStudy memberData, StudyType studyType){
        switch (studyType) {
            case MAJOR -> memberData.setMajor(2.5);
            case NON_MAJOR -> memberData.setNonMajor(2.0);
            case STUDY -> memberData.setStudy(2.0);
        }
    }

    private void setIsOnline(MemberDataStudy memberData, Boolean isOnline){
        memberData.setIsOnline(isOnline ? 2.0 : 0.0);
    }


    private void setStudyTimes(MemberDataStudy memberData, int studyTimes){
        // 스터디 횟수를 0.0 ~ 2.0 사이의 값으로 정규화
        memberData.setStudyTimes((double) studyTimes / 5);  // 예: 최대 10회 기준
    }
}