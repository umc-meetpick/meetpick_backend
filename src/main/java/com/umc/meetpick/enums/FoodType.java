package com.umc.meetpick.enums;

import lombok.Getter;

@Getter
public enum FoodType {
    KOREAN("한식"),
    WESTERN("양식"),
    JAPANESE("일식"),
    CHINESE("중식"),
    VIETNAMESE("베트남식"),
    OTHER("기타");

    public static FoodType fromString(String foodType) {
        switch (foodType) {
            case "한식":
                return KOREAN;
            case "양식":
                return WESTERN;
            case "일식":
                return JAPANESE;
            case "중식":
                return CHINESE;
            case "베트남식":
                return VIETNAMESE;
            case "기타":
                return OTHER;
            default:
                throw new IllegalArgumentException("foodType 오류");
        }
    }

    private final String koreanName;

    FoodType(String koreanName) {
        this.koreanName = koreanName;
    }

}
