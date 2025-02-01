package com.umc.meetpick.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class StudentNumberDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentNumberRequestDTO {

        @NotNull(message = "학번을 입력해야 합니다.")
        @Min(value = 0, message = "숫자만 입력하세요.")
        private String studentNumber;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentNumberResponseDTO {
        private Long memberId;
        private Long memberProfileId;
        private int studentNumber;
    }
}
