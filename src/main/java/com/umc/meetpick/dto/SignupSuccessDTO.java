package com.umc.meetpick.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupSuccessDTO {
    private Long memberId;
    private String message;
}
