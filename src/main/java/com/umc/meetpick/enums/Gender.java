package com.umc.meetpick.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("남성"),
    FEMALE("여성");

    private final String koreanName;

    Gender(String koreanName) {
        this.koreanName = koreanName;
    }

}
