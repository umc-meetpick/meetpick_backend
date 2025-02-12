package com.umc.meetpick.enums;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import lombok.Getter;

@Getter
public enum Gender {
    MALE("남성"),
    FEMALE("여성");

    private final String koreanName;

    public static Gender fromString(String koreanName) {
        for (Gender gender : Gender.values()) {
            if (gender.koreanName.equals(koreanName)) {
                return gender;
            }
        }
        throw new GeneralHandler(ErrorCode.INVALID_ENUM);
    }

    Gender(String koreanName) {
        this.koreanName = koreanName;
    }

}
