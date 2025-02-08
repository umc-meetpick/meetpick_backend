package com.umc.meetpick.dto;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileDetailResponseDto {

    // 1. 기본 프로필 정보
    private String nickname;           // From MemberProfile.nickname
    private String gender;            // From Member.gender
    private int age;                  // From Member
    private int studentNumber;        // From MemberProfile
    private String mbti;              // From MemberProfile

    // 매칭 선호도 정보-내부 클래스
    private PreferenceInfo preferenceInfo;

    //매칭 상태 정보-내부 클래스
    private boolean isLiked;          // From MemberSecondProfileLikes
    private SlotInfo slotInfo;

    //매칭 선호도 내부 클래스 PreferenceInfo
    @Getter
    @Builder
    public static class PreferenceInfo {
        private MateType mateType;            // From MemberSecondProfile.mateType
        private String preferredGender;        // From MemberSecondProfile.gender의 koreanName
        private Integer minAge;                // From MemberSecondProfile.minAge
        private Integer maxAge;                // From MemberSecondProfile.maxAge
        private StudentNumber studentNumber;   // From MemberSecondProfile.studentNumber

        // MateType
        private Set<ExerciseType> exerciseTypes;  // From MemberSecondProfile.exerciseTypes (EXERCISE 타입일 때만)
        private Set<FoodType> foodTypes;          // From MemberSecondProfile.foodTypes (MEAL 타입일 때만)
    }

    // 슬롯 정보 내부 클래스 SlotInfo
    @Getter
    @Builder
    public static class SlotInfo {
        private int currentPeople;    // From MemberSecondProfile.currentPeople
        private int maxPeople;        // From MemberSecondProfile.maxPeople
    }

    // entity->DTO
    public static ProfileDetailResponseDto from(
            Member member,
            MemberSecondProfile secondProfile,
            boolean isLiked
    ) {
        MemberProfile memberProfile = member.getMemberProfile();

        // SlotInfo 생성
        SlotInfo slotInfo = SlotInfo.builder()
                .currentPeople(secondProfile.getCurrentPeople())
                .maxPeople(secondProfile.getMaxPeople())
                .build();

        // PreferenceInfo 생성
        PreferenceInfo preferenceInfo = PreferenceInfo.builder()
                .mateType(secondProfile.getMateType())
                .preferredGender(secondProfile.getGender() != null ?
                        secondProfile.getGender().getKoreanName() : null)
                .minAge(secondProfile.getMinAge())
                .maxAge(secondProfile.getMaxAge())
                .studentNumber(secondProfile.getStudentNumber())
                .exerciseTypes(secondProfile.getMateType() == MateType.EXERCISE ?
                        secondProfile.getExerciseTypes() : null)
                .foodTypes(secondProfile.getMateType() == MateType.MEAL ?
                        secondProfile.getFoodTypes() : null)
                .build();

        // 최종 DTO 생성
        return ProfileDetailResponseDto.builder()
                .nickname(memberProfile.getNickname())
                .gender(member.getGender().getKoreanName())
                .age(member.getAge())
                .studentNumber(memberProfile.getStudentNumber())
                .mbti(memberProfile.getMBTI().name())
                .preferenceInfo(preferenceInfo)
                .isLiked(isLiked)
                .slotInfo(slotInfo)
                .build();
    }

}
