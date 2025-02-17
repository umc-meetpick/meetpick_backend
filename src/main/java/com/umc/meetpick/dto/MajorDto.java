package com.umc.meetpick.dto;

import lombok.*;

import java.util.List;

public class MajorDto {

    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubMajorInfoDto {
        private String subMajorName;
        private Long subMajorId;
    }

    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MajorInfoDto {
        private String majorName;
        private List<SubMajorInfoDto> subMajorInfoDtoList;
    }

    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfoDto {
        private List<MajorInfoDto> subMajors;
    }
}
