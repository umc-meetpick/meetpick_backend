package com.umc.meetpick.dto;

import lombok.*;

public class ProfileImageDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileImageRequestDTO {
        private String imageUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileImageResponseDTO {
        private Long memberId;
        private Long memberProfileId;
        private String profileImage;
    }
}
