package com.umc.meetpick.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import lombok.Getter;

@Getter
public enum MateType {
    
    STUDY("공부"),
    EXERCISE("운동"),
    MEAL("혼밥"),
    ALL("전체");

    private final String koreanName;

    MateType(String koreanName) {
        this.koreanName = koreanName;
    }

    //json직렬화/역직렬화
    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }
    public static MateType fromString(String type) {
        for (MateType mateType : MateType.values()) {
            if (mateType.koreanName.equalsIgnoreCase(type)) {
                return mateType;
            }
        }
        throw new GeneralHandler(ErrorCode.INVALID_MATE_TYPE);
    }
}