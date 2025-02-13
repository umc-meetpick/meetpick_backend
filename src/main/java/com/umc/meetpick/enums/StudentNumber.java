package com.umc.meetpick.enums;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import lombok.Getter;

@Getter
public enum StudentNumber {
    SENIOR ("선배"),
    JUNIOR ("후배"),
    PEER ("동기"),
    ALL ("상관없어");

    StudentNumber(String koreanName) {
        this.koreanName = koreanName;
    }

    private final String koreanName;

    public static StudentNumber fromString(String studentNumber) {
        return switch (studentNumber) {
            case "동기" -> PEER;
            case "선배" -> SENIOR;
            case "후배" -> JUNIOR;
            case "상관없어" -> ALL;
            default -> throw new GeneralHandler(ErrorCode._BAD_REQUEST);
        };
    }

}
