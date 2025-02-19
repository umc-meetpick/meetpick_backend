package com.umc.meetpick.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class KakaoLoginResponseDTO {
    private String jwtToken;  //  JWT 토큰 추가
    private String accessToken;
    private Long memberId;
}
