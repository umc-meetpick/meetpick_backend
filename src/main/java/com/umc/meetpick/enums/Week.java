package com.umc.meetpick.enums;

public enum Week {
    MON,
    TUE,
    WED,
    THRU,
    FRI;
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
}

