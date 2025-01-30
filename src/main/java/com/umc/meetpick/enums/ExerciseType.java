package com.umc.meetpick.enums;

public enum ExerciseType {
    BOWLING("볼링"),
    CLIMBING("클라이밍"),
    TABLE_TENNIS("탁구"),
    FITNESS("🏋헬스"),
    RUNNING("러닝/조깅"),
    SOCCER("축구/풋살"),
    BASKETBALL("농구"),
    TENNIS_BADMINTON("테니스/배드민턴"),
    OTHER("기타");

    private final String displayName;

    ExerciseType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
