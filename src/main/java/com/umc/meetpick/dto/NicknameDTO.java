package com.umc.meetpick.dto;

import lombok.*;

public class NicknameDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NicknameRequestDTO {
        private String nickname;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NicknameResponseDTO {
        private Long memberId;
        private Long memberProfileId;
        private String nickname;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NicknameCheckResponseDTO {
        private boolean isAvailable;
    }
}
