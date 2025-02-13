package com.umc.meetpick.enums;

import lombok.Getter;

@Getter
public enum Week {
    MON ("월"),
    TUE ("화"),
    WED ("수"),
    THRU ("목"),
    FRI ("금");

    Week(String koreanName) {
        this.koreanName = koreanName;
    }

    public static Week fromString(String week) {
        switch (week) {
            case "월":
                return MON;
            case "화":
                return TUE;
            case "수":
                return WED;
            case "목":
                return THRU;
            case "금":
                return FRI;
            default:
                throw new IllegalArgumentException("week 오류");
        }
    }

    private final String koreanName;
}

