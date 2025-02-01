package com.umc.meetpick.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class KakaoLoginResponseDTO {
    private String accessToken;
    private Long memberId;
}
