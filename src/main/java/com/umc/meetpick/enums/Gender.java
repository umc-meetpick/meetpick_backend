package com.umc.meetpick.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Gender {
    MALE("남성"),
    FEMALE("여성");

    private final String koreanName;

    Gender(String koreanName) {
        this.koreanName = koreanName;
    }


    // json직렬화/역직렬화를 위해 추가
    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }

    public static Gender fromString(String type) {
        for (Gender gender : Gender.values()) {
            if (gender.koreanName.equalsIgnoreCase(type)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("gender 오류");
    }
}
