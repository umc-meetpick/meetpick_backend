package com.umc.meetpick.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileDetailListResponseDto {
    private List<ProfileDetailResponseDto> profiles;    // 프로필 목록
    private int totalPages;                            // 전체 페이지 수
    private long totalElements;                        // 전체 요소 수
    private boolean hasNext;                           // 다음 페이지 존재 여부

    // entity->DTO 변환
    public static ProfileDetailListResponseDto from(
            List<ProfileDetailResponseDto> profiles,
            int totalPages,
            long totalElements,
            boolean hasNext
    ) {
        return ProfileDetailListResponseDto.builder()
                .profiles(profiles)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .hasNext(hasNext)
                .build();
    }
}