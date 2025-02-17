package com.umc.meetpick.enums;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import lombok.Getter;

@Getter
public enum Hobby {
    GAMING("게임"),
    WRITING("글쓰기"),
    DRAWING("드로잉"),
    ACTING("연기"),
    LISTENING_TO_MUSIC("음악 듣기"),
    COOKING("요리"),
    PUZZLE_SOLVING("퍼즐 맞추기"),
    HANDMADE("핸드메이드"),
    READING("독서"),
    CAMPING("캠핑"),
    GARDENING("꽃꽂이"),
    HIKING("등산"),
    CYCLING("자전거"),
    SOCCER("축구"),
    BADMINTON("배드민턴"),
    TENNIS("테니스"),
    FITNESS("헬스"),
    SWIMMING("수영"),
    VIOLIN("바이올린"),
    PAINTING("회화"),
    COIN_COLLECTING("동전 수집"),
    STAMP_COLLECTING("우표 수집"),
    YOGA("요가"),
    TRAVELING("여행"),
    DEVELOPMENT("개발");

    private final String koreanName;

    Hobby(String koreanName) {
        this.koreanName = koreanName;
    }

    public static Hobby fromString(String koreanName) {
        for (Hobby hobby : Hobby.values()) {
            if (hobby.koreanName.equals(koreanName)) {
                return hobby;
            }
        }
        throw new GeneralHandler(ErrorCode.INVALID_ENUM);
    }
}