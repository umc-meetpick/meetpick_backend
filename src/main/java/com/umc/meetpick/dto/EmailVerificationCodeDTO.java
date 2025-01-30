package com.umc.meetpick.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailVerificationCodeDTO {
    private Long memberId;
    private String email;
    private String univName;
    private int verificationCode;
}
