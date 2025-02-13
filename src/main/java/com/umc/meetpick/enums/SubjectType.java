package com.umc.meetpick.enums;

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
}