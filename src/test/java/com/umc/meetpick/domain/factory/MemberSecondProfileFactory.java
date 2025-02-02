package com.umc.meetpick.domain.factory;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.Personality;
import com.umc.meetpick.entity.mapping.MemberSecondProfileSubMajor;
import com.umc.meetpick.entity.mapping.MemberSecondProfileTimes;
import com.umc.meetpick.enums.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MemberSecondProfileFactory {

    public static MemberSecondProfile create(
            Member member,
            Gender gender,
            List<MemberSecondProfileSubMajor> memberSecondProfileMajorList,
            StudentNumber studentNumber,
            int minAge,
            int maxAge,
            Set<Personality> personality,
            Set<MBTI> mbti,
            boolean isHobbySame,
            List<MemberSecondProfileTimes> memberSecondProfileTimes,
            int maxPeople,
            int currentPeople,
            String comment,
            Set<ExerciseType> exerciseTypes,
            Boolean isSchool,
            Set<FoodType> foodTypes,
            MateType mateType
    ) {
        return MemberSecondProfile.builder()
                .member(member)
                .gender(gender)
                .memberSecondProfileMajorList(memberSecondProfileMajorList != null ? memberSecondProfileMajorList : List.of())
                .studentNumber(studentNumber)
                .minAge(minAge)
                .maxAge(maxAge)
                .personality(personality != null ? personality : Set.of())
                .mbti(mbti != null ? mbti : Set.of())
                .isHobbySame(isHobbySame)
                .memberSecondProfileTimes(memberSecondProfileTimes != null ? memberSecondProfileTimes : List.of())
                .maxPeople(maxPeople)
                .currentPeople(currentPeople)
                .comment(comment)
                .exerciseTypes(exerciseTypes != null ? exerciseTypes : Set.of())
                .isSchool(isSchool)
                .foodTypes(foodTypes != null ? foodTypes : Set.of())
                .mateType(mateType)
                .build();
    }

    public static List<MemberSecondProfile> createMultiple(List<Member> members) {

        return members.stream()
                .map(member -> {
                    MemberSecondProfile profile = create(
                            member,
                            null,
                            List.of(),
                            null,
                            18,
                            28,
                            Set.of(),
                            Set.of(),
                            false,
                            List.of(),
                            10,
                            0,
                            "",
                            Set.of(),
                            null,
                            Set.of(),
                            MateType.STUDY
                    );
                    member.setMemberSecondProfile(profile); // Member에 SecondProfile 설정
                    return profile;
                })
                .collect(Collectors.toList());
    }
}

