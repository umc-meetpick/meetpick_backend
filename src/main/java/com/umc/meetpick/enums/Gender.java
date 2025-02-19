package com.umc.meetpick.enums;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Gender {
    MALE("남성"),
    FEMALE("여성"),
    ALL("상관 없어");

    private final String koreanName;

    Gender(String koreanName) {
        this.koreanName = koreanName;
    }

    // json직렬화/역직렬화를 위해 추가
    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }

    public static Gender fromString(String koreanName) {
        for (Gender gender : Gender.values()) {
            if (gender.koreanName.equals(koreanName)) {
                return gender;
            }
        }
        throw new GeneralHandler(ErrorCode.INVALID_ENUM);
    }

}
