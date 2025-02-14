package com.umc.meetpick.enums;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import lombok.Getter;

@Getter
public enum ContactType{

    KAKAO_TALK_ID ("카카오톡"),    // 카카오톡ID
    OPEN_CHAT_LINK ("오픈채팅"),   // 오픈채팅링크
    PHONE_NUMBER ("전화번호");      // 전화번호

    private final String koreanName;

    ContactType(String koreanName){this.koreanName = koreanName;}

    public static ContactType fromString(String koreanName) {
        for (ContactType contactType : ContactType.values()) {
            if (contactType.koreanName.equals(koreanName)) {
                return contactType;
            }
        }
        throw new GeneralHandler(ErrorCode.INVALID_ENUM);
    }
}
