package com.umc.meetpick.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchPageDto {
    List<MatchRequestDto> matchRequestDtoList;
    private int currentPage;
    private boolean hasNextPage;
}
