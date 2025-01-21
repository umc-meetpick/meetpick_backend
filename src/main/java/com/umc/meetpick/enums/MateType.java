package com.umc.meetpick.enums;

public enum MateType {
    STUDY,
    EXERCISE,
    MEAL;

    public static MateType fromString(String type) {
        for (MateType mateType : MateType.values()) {
            if (mateType.name().equalsIgnoreCase(type)) {
                return mateType;
            }
        }
        throw new IllegalArgumentException("Unknown MateType: " + type);
    }
}
