package com.umc.meetpick.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor  // ✅ 필드에 맞는 생성자 추가
public class TermsDTO {
    private Long memberId;
    private boolean termsAgreed;
}
