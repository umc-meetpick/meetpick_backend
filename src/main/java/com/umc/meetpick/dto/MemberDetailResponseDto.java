package com.umc.meetpick.dto;

import com.umc.meetpick.entity.WeekPair;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.Hobby;
import com.umc.meetpick.enums.MBTI;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MemberDetailResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberCommonDetailDto{
        long memberId;
        String age;
        String studentNumber;
        String gender;
        MBTI mbti;
        String major;
        String subMajor;
        Set<String> hobbies;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberFoodDetailDto{
        String gender;
        String ageAndPeer;
        Set<String> foodTypes;
        String currentPeople;
        Set<String> major;
        String hobby;
        String MBTI;
        List<WeekPair<String, String>> weekAndTime;
        String comment;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberExerciseDetailDto{
        String exercise;
        String currentPeople;
        String gender;
        String ageAndPeer;
        Set<String> major;
        String MBTI;
        String hobby;
        List<WeekPair<String, String>> weekAndTime;
        String place;
        String comment;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberStudyDetailDto {
        String study;
        Set<String> major;
        String courseName;
        String professorName;
        String studyTimes;
        String isOnline;
        String gender;
        String ageAndPeer;
        String currentPeople;
        String MBTI;
        String place;
        List<WeekPair<String, String>> weekAndTime;
        String hobby;
        String comment;

    }
}
