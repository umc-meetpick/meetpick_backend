package com.umc.meetpick.common.response.status;

import com.umc.meetpick.common.response.BaseCode;
import com.umc.meetpick.common.response.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode implements BaseCode {

    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),

    // 회원가입 응답
    _SIGNUP_SUCCESS(HttpStatus.OK, "SIGNUP200", "회원가입 성공입니다."),
    _LOGIN_SUCCESS(HttpStatus.OK, "LOGIN200", "로그인 성공입니다."),
    _ADDITIONAL_INFO(HttpStatus.OK, "LOGIN201", "로그인 성공. 회원가입 필요합니다");
    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
