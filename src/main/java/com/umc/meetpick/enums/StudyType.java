package com.umc.meetpick.enums;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;

public enum StudyType {
    MAJOR("전공"),
    NON_MAJOR("교양"),
    STUDY("스터디");

    public static StudyType fromString(String type) {
        for (StudyType studyType : StudyType.values()) {
            if (studyType.koreanName.equals(type)) {
                return studyType;
            }
        }

        throw new GeneralHandler(ErrorCode.INVALID_ENUM);
    }

    private final String koreanName;

    StudyType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}
