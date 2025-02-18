package com.umc.meetpick.service.matching.processor.food;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileSubMajor;
import com.umc.meetpick.entity.matchingdata.food.MemberRequestData;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.repository.food.MemberRequestDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MatchingDataProcessor {

    private final MemberRequestDataRepository memberRequestDataRepository;

    public void process(MemberSecondProfile memberSecondProfile){

        Member member = memberSecondProfile.getMember();

        MemberRequestData memberRequestData = MemberRequestData.builder().build();
        setGender(memberRequestData, memberSecondProfile.getGender());
        setAge(memberRequestData, memberSecondProfile.getMinAge(), memberSecondProfile.getMaxAge());
        setMbti(memberRequestData, memberSecondProfile.getMbti());
        if(memberSecondProfile.getMemberSecondProfileMajorList() != null){
            setMajor(memberRequestData, memberSecondProfile.getMemberSecondProfileMajorList());
        }
        setFoodType(memberRequestData, memberSecondProfile.getFoodTypes());
        memberRequestData.setMember(memberSecondProfile.getMember());
        memberRequestData.setUniversity(member.getUniversity());

        memberRequestDataRepository.save(memberRequestData);

    }

    private void setGender(MemberRequestData memberRequestData, Gender gender){
        switch (gender) {
            case MALE -> memberRequestData.setGender(0.0);
            case FEMALE -> memberRequestData.setGender(3.0);
            case ALL -> memberRequestData.setGender(1.5);
        };
    }

    private void setAge(MemberRequestData memberRequestData, Integer minAge, Integer maxAge){
        memberRequestData.setAge((double) ((maxAge + minAge) / 2));
    }

    private void setMbti(MemberRequestData memberRequestData, String mbti){

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

    private void setMajor(MemberRequestData memberRequestData, List<MemberSecondProfileSubMajor> secondProfileSubMajors){
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

    private void setFoodType(MemberRequestData memberRequestData, Set<FoodType> foodType){
        foodType.forEach(
                type -> {
                    switch (type) {
                        case KOREAN -> memberRequestData.setKOREAN(2.0);
                        case WESTERN -> memberRequestData.setWESTERN(2.0);
                        case JAPANESE -> memberRequestData.setJAPANESE(2.0);
                        case CHINESE -> memberRequestData.setCHINESE(2.0);
                        case VIETNAMESE -> memberRequestData.setVIETNAMESE(2.0);
                    }
                }
        );

    }
}
