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

    public static ExerciseType fromString(String exerciseType) {
        switch (exerciseType) {
            case "볼링":
                return BOWLING;
            case "클라이밍":
                return CLIMBING;
            case "탁구":
                return TABLE_TENNIS;
            case "헬스":
                return FITNESS;
            case "러닝/조깅":
                return RUNNING;
            case "축구/풋살":
                return SOCCER;
            case "농구":
                return BASKETBALL;
            case "테니스/배드민턴":
                return TENNIS_BADMINTON;
            case "기타":
                return OTHER;
            default:
                throw new IllegalArgumentException("week 오류");
        }
    }

    private final String displayName;

    ExerciseType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
