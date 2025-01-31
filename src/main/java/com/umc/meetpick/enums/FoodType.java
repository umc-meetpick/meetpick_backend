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

    private final String koreanName;

    FoodType(String koreanName) {
        this.koreanName = koreanName;
    }

}
