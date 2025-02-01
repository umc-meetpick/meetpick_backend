package com.umc.meetpick.dto;

import lombok.*;

public class MajorDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MajorRequestDTO {
        private Long subMajorId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MajorResponseDTO {
        private Long memberId;
        private Long subMajorId;
        private String subMajorName;
        private Long majorId;
        private String majorName;
    }
}
