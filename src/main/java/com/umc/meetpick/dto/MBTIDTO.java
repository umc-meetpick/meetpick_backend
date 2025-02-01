package com.umc.meetpick.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public class MBTIDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MBTIRequestDTO {

        @JsonProperty("MBTI") // JSON 매핑을 강제 적용
        private String MBTI;

        public void setMBTI(String MBTI) {
            this.MBTI = (MBTI != null) ? MBTI.toUpperCase() : null;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MBTIResponseDTO {
        private Long memberId;
        private Long memberProfileId;
        private String MBTI;
        private String message;
    }
}
