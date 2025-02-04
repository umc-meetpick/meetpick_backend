package com.umc.meetpick.dto;

import com.umc.meetpick.enums.ContactType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ContactDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContactRequestDTO {
        private Long memberId;
        private ContactType contactType;  // 연락처 유형 (카카오톡 ID, 오픈채팅링크, 전화번호)
        private String contactInfo;  // 해당 연락처 정보
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContactResponseDTO {
        private Long memberProfileId;  // member_profile의 id
        private ContactType contactType;
        private String contactInfo;
    }
}
