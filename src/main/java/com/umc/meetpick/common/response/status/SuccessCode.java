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

    // 회원가입 및 로그인 관련 응답
    _SIGNUP_SUCCESS(HttpStatus.OK, "SIGNUP200", "회원가입 성공입니다."),
    _VERIFY_SUCCESS(HttpStatus.OK, "VERIFY200", "대학교 인증 성공입니다."),
    _LOGIN_SUCCESS(HttpStatus.OK, "LOGIN200", "로그인 성공입니다."),
    //닉네임 관련 응답
    NICKNAME_AVAILABLE(HttpStatus.OK, "NICKNAME200", "사용 가능한 닉네임입니다."),
    NICKNAME_SET_SUCCESS(HttpStatus.OK, "NICKNAME201", "닉네임이 설정되었습니다."),
    // 프로필 관련 응답
    PROFILE_IMAGE_UPDATED(HttpStatus.OK, "PROFILE_IMAGE200", "프로필 이미지가 설정되었습니다."),
    // 학번 관련 응답
    STUDENT_NUMBER_SET_SUCCESS(HttpStatus.OK, "STUDENT_NUMBER200", "학번이 설정되었습니다."),

    MBTI_SET_SUCCESS(HttpStatus.OK, "MBTI2001", "MBTI가 설정되었습니다."),

    MAJOR_SET_SUCCESS(HttpStatus.OK, "MAJOR200", "전공이 설정되었습니다."),
    // 취미 설정 성공 코드
    HOBBY_SET_SUCCESS(HttpStatus.OK, "HOBBY200", "취미가 성공적으로 설정되었습니다."),
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
