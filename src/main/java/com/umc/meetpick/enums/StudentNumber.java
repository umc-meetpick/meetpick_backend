package com.umc.meetpick.enums;

public enum StudentNumber {
    SENIOR("선배"),
    JUNIOR("후배"),
    PEER("동기"),
    ALL("상관 없음");

    private final String koreanName;

    StudentNumber(String koreanName) {
        this.koreanName = koreanName;
    }
}
