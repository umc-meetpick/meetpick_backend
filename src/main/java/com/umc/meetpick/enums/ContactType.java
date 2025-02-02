package com.umc.meetpick.enums;

import lombok.Getter;

@Getter
public enum ContactType{
    KAKAO_TALK_ID("카카오톡 ID"),    // 카카오톡ID
    OPEN_CHAT_LINK("오픈채팅 링크"),   // 오픈채팅링크
    PHONE_NUMBER("전화번호");      // 전화번호

    private final String koreanName;

    ContactType(String koreanName) {
        this.koreanName = koreanName;
    }
}
