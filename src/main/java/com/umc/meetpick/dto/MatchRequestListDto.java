package com.umc.meetpick.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchRequestListDto {
    private List<MatchRequestDto> requests;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;
}