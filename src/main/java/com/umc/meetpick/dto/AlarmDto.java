package com.umc.meetpick.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class AlarmDto {

    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlarmRequestDto {
        private String mateType;
        private int page;
        private int size;

        public Pageable toPageable() {
            return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlarmResponseDto {
        String mateType;
        String content;
        String createdAt;
        Long mappingId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlarmPageResponseDto {
        private List<AlarmResponseDto> alarms;  // 알람 목록
        private int currentPage;
        private boolean hasNextPage;
    }

}
