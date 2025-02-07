package com.umc.meetpick.enums;

public enum StudentNumber {
    SENIOR,
    JUNIOR,
    PEER;
    public static StudentNumber fromString(String studentNumber) {
        switch (studentNumber) {
            case "동기":
                return PEER;
            case "선배":
                return SENIOR;
            case "후배":
                return JUNIOR;
            default:
                throw new IllegalArgumentException("studentNumber 오류");
        }
    }
}
