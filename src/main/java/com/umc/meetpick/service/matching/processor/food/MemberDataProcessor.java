package com.umc.meetpick.service.matching.processor.food;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.matchingdata.food.MemberData;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.repository.food.MemberDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MemberDataProcessor {

    private final MemberDataRepository memberDataRepository;

    public void process(MemberSecondProfile memberSecondProfile){

        Member member = memberSecondProfile.getMember();
        MemberProfile memberProfile = member.getMemberProfile();

        MemberData memberData = MemberData.builder().build();
        setGender(memberData, member.getGender());
        setAge(memberData, member.getAge());
        setMbti(memberData, memberProfile.getMBTI().toString());
        setMajor(memberData, memberProfile.getSubMajor().getMajor().getName());
        setFoodType(memberData, memberSecondProfile.getFoodTypes());
        memberData.setMember(member);
        memberData.setUniversity(member.getUniversity());

        memberDataRepository.save(memberData);
    }


    private void setGender(MemberData memberData, Gender gender){
        switch (gender) {
            case MALE -> memberData.setGender(0.0);
            case FEMALE -> memberData.setGender(3.0);
            case ALL -> memberData.setGender(1.5);
        };
    }

    private void setAge(MemberData memberData, Integer Age){
        memberData.setAge((double) (Age));
    }

    private void setMbti(MemberData memberData, String mbti){

        if(mbti.charAt(0) == 'I'){
            memberData.setIE(0.8);
        }

        if(mbti.charAt(1) == 'S'){
            memberData.setSN(0.8);
        }

        if(mbti.charAt(2) == 'T'){
            memberData.setTF(0.8);
        }

        if(mbti.charAt(3) == 'J'){
            memberData.setJP(0.8);
        }
    }

    private void setMajor(MemberData memberData, String majorName){
                    switch (majorName) {
                        case "공학 계열":
                            memberData.setEngineering(0.5);
                        case "자연과학 계열":
                            memberData.setScience(0.5);
                        case "인문학 계열":
                            memberData.setHumanities(0.5);
                        case "사회과학 계열":
                            memberData.setSocialScience(0.5);
                        case "의학 계열":
                            memberData.setMedicine(0.5);
                        case "예술·체육 계열":
                            memberData.setArtsAndPhysical(0.5);
                        case "농·생명 계열":
                            memberData.setAgricultureAndLife(0.5);
                        case "융합/특성화 계열":
                            memberData.setConvergenceAndSpecialization(0.5);
                    }
    }

    private void setFoodType(MemberData memberData, Set< FoodType > foodType){
        foodType.forEach(
                type -> {
                    switch (type) {
                        case KOREAN -> memberData.setKOREAN(2.0);
                        case WESTERN -> memberData.setWESTERN(2.0);
                        case JAPANESE -> memberData.setJAPANESE(2.0);
                        case CHINESE -> memberData.setCHINESE(2.0);
                        case VIETNAMESE -> memberData.setVIETNAMESE(2.0);
                    }
                }
        );

    }
}
