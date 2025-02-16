package com.umc.meetpick.dto;

import com.umc.meetpick.enums.MBTI;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

public class RecommendDto {

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
    public static class FoodRecommendDto {
        private Long memberSecondProfileId;
        private String nickName;
        private String studentNumber;
        private Set<String> foodTypes;
        private String gender;
        private MBTI mbti;
    }
}
