package com.umc.meetpick.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum SubjectType {
    MAJOR("전공"),
    LIBERAL_ARTS("교양"),
    CERTIFICATE("자격증");

    private final String koreanName;

    SubjectType(String koreanName) {
        this.koreanName = koreanName;
    }


    // json직렬화/역직렬화를 위해 추가
    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }

    public static SubjectType fromString(String type) {
        for (SubjectType subjectType : SubjectType.values()) {
            if (subjectType.koreanName.equalsIgnoreCase(type)) {
                return subjectType;
            }
        }
        throw new IllegalArgumentException("subjectType 오류");
    }
}