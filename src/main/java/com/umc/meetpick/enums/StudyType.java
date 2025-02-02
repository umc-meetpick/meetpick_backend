package com.umc.meetpick.enums;

import lombok.Getter;

@Getter
public enum StudyType {
    MAJOR("전공"),
    GENERAL("교양"),
    ALL("모두");

    private final String koreanName;

    StudyType(String koreanName) {
        this.koreanName = koreanName;
    }
}
