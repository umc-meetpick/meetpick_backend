package com.umc.meetpick.service.request.factory;

import com.umc.meetpick.dto.LikeResponseDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileLikes;
import com.umc.meetpick.enums.FoodType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LikeDtoFactory {

    public static List<LikeResponseDto.FoodLikeResponseDto> getFoodLikeResponseDto(List<MemberSecondProfileLikes> memberSecondProfileLikesList){

        return memberSecondProfileLikesList.stream()
                .map(memberSecondProfileLikes -> {

                    MemberSecondProfile memberSecondProfile = memberSecondProfileLikes.getMemberSecondProfile();
                    Member member = memberSecondProfile.getMember();
                    MemberProfile memberProfile = member.getMemberProfile();

                   LikeResponseDto.MemberProfileDto memberProfileDto = LikeResponseDto.MemberProfileDto.builder()
                            .profileId(memberSecondProfile.getId())
                            .nickName(memberProfile.getNickname())
                            .gender(member.getGender().getKoreanName())
                            .profileAge(member.getAge() + "살")
                            .studentNumber(memberProfile.getStudentNumber() + "학번")
                            .mbti(memberProfile.getMBTI().toString())
                            .build();

                    return LikeResponseDto.FoodLikeResponseDto.builder()
                            .memberProfile(memberProfileDto)
                            .foodTypes(memberSecondProfile.getFoodTypes().stream().map(FoodType::getKoreanName).collect(Collectors.toSet()))
                            .age(memberSecondProfile.getMinAge() + " ~ " + memberSecondProfile.getMaxAge())
                            .isPeer(memberSecondProfile.getStudentNumber().getKoreanName())
                            .gender(memberSecondProfile.getGender().getKoreanName())
                            .build();


                })
                .toList();
    }

    public static List<LikeResponseDto.ExerciseLikeResponseDto> getExerciseLikeResponseDto(List<MemberSecondProfileLikes> memberSecondProfileLikesList){

        return memberSecondProfileLikesList.stream()
                .map(memberSecondProfileLikes -> {

                    MemberSecondProfile memberSecondProfile = memberSecondProfileLikes.getMemberSecondProfile();
                    Member member = memberSecondProfile.getMember();
                    MemberProfile memberProfile = member.getMemberProfile();

                    LikeResponseDto.MemberProfileDto memberProfileDto = LikeResponseDto.MemberProfileDto.builder()
                            .profileId(memberSecondProfile.getId())
                            .nickName(memberProfile.getNickname())
                            .profileAge(member.getAge() + "살")
                            .gender(member.getGender().getKoreanName())
                            .studentNumber(memberProfile.getStudentNumber() + "학번")
                            .mbti(memberProfile.getMBTI().toString())
                            .build();

                    return LikeResponseDto.ExerciseLikeResponseDto.builder()
                            .memberProfile(memberProfileDto)
                            .exerciseType(memberSecondProfile.getExerciseType().getDisplayName())
                            .age(memberSecondProfile.getMinAge() + " ~ " + memberSecondProfile.getMaxAge())
                            .isPeer(memberSecondProfile.getStudentNumber().getKoreanName())
                            .gender(memberSecondProfile.getGender().getKoreanName())
                            .build();


                })
                .toList();
    }

    public static List<LikeResponseDto.StudyLikeResponseDto> getStudyLikeResponseDto(List<MemberSecondProfileLikes> memberSecondProfileLikesList){

        return memberSecondProfileLikesList.stream()
                .map(memberSecondProfileLikes -> {

                    MemberSecondProfile memberSecondProfile = memberSecondProfileLikes.getMemberSecondProfile();
                    Member member = memberSecondProfile.getMember();
                    MemberProfile memberProfile = member.getMemberProfile();

                    LikeResponseDto.MemberProfileDto memberProfileDto = LikeResponseDto.MemberProfileDto.builder()
                            .profileId(memberSecondProfile.getId())
                            .nickName(memberProfile.getNickname())
                            .profileAge(member.getAge() + "살")
                            .gender(member.getGender().getKoreanName())
                            .studentNumber(memberProfile.getStudentNumber() + "학번")
                            .mbti(memberProfile.getMBTI().toString())
                            .build();

                    return LikeResponseDto.StudyLikeResponseDto.builder()
                            .memberProfile(memberProfileDto)
                            .studyType(memberSecondProfile.getStudyType().getKoreanName())
                            .age(memberSecondProfile.getMinAge() + " ~ " + memberSecondProfile.getMaxAge())
                            .isPeer(memberSecondProfile.getStudentNumber().getKoreanName())
                            .gender(memberSecondProfile.getGender().getKoreanName())
                            .build();

                })
                .toList();
    }
}
