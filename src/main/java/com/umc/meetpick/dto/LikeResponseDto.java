package com.umc.meetpick.dto;

import com.umc.meetpick.enums.MateType;
import lombok.*;

import java.util.Set;


public class LikeResponseDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberProfileDto {
        private Long profileId;
        private String profileAge;
        private String nickName;
        private String gender;
        private String studentNumber;
        private String mbti;
        private Boolean isLiked;
        private String imageUrl;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FoodLikeResponseDto {
        private MemberProfileDto memberProfile;
        private Set<String> foodTypes;
        private String age;
        private String isPeer;
        private String gender;
        private Integer currentPeople;
        private Integer maxPeople;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExerciseLikeResponseDto {
        private MemberProfileDto memberProfile;
        private String exerciseType;
        private String age;
        private String isPeer;
        private String gender;
        private Integer currentPeople;
        private Integer maxPeople;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudyLikeResponseDto {
        private MemberProfileDto memberProfile;
        private String studyType;
        private String age;
        private String isPeer;
        private String gender;
        private Integer currentPeople;
        private Integer maxPeople;
    }

}
