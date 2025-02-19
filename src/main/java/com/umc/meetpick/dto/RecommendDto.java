package com.umc.meetpick.dto;

import com.umc.meetpick.enums.ExerciseType;
import com.umc.meetpick.enums.MBTI;
import com.umc.meetpick.enums.StudyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

public class RecommendDto {

   // public interface RecommendPageDto {
     //   boolean isHasNextPage();
       // int getCurrentPage();
    //}

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FoodRecommendPageDto {
        private List<FoodRecommendDto> foodRecommendDtos;
        private int currentPage;
        private boolean hasNextPage;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FoodRecommendDto  {
        private Long memberSecondProfileId;
        private String nickName;
        private String studentNumber;
        private Set<String> foodTypes;
        private String gender;
        private MBTI mbti;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExerciseRecommendPageDto{
        private List<ExerciseRecommendDto> exerciseRecommendDtos;
        private boolean hasNextPage;
        private int currentPage;
    }

    @Getter
    @Builder
    public static class ExerciseRecommendDto {
        private Long memberSecondProfileId;
        private String studentNumber;
        private String gender;
        private MBTI mbti;
        private String nickName;
        private ExerciseType exerciseType;
        private Boolean isSchool;
        private String place;
    }

    @Getter
    @Builder
    public static class StudyRecommendPageDto {
        private List<StudyRecommendDto> studyRecommendDtos;
        private boolean hasNextPage;
        private int currentPage;
    }

    @Getter
    @Builder
    public static class StudyRecommendDto {
        private Long memberSecondProfileId;
        private String studentNumber;
        private String gender;
        private MBTI mbti;
        private String nickName;
        private StudyType studyType;
        private String majorName;
        private String professorName;
        private Boolean isOnline;
        private Integer studyTimes;
        private String place;
    }
}

