package com.umc.meetpick.common.response.status;

import com.umc.meetpick.common.response.BaseErrorCode;
import com.umc.meetpick.common.response.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    // 서버 관련 에러
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER5001", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),

    // 멤버 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    MEMBER_LOGIN_FAILURE(HttpStatus.BAD_REQUEST, "MEMBER4003", "아이디 혹은 비밀번호를 잘못 입력하였습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),

    MEMBER_SIGNUP_ERROR(HttpStatus.BAD_REQUEST, "SIGNUP4001", "회원가입 유효성 검사 실패"),
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "SIGNUP4002", "이미 존재하는 이메일입니다."),

    POST_NOTFOUND(HttpStatus.BAD_REQUEST, "POST4004", "게시물을 찾을 수 없습니다."),

    UNSIGNED(HttpStatus.BAD_REQUEST, "POST4001", "로그인 되어 있지 않습니다."),

    // 닉네임 관련 에러
    NICKNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "NICKNAME4001", "이미 존재하는 닉네임입니다."),
    NICKNAME_NOT_PROVIDED(HttpStatus.BAD_REQUEST, "NICKNAME4002", "닉네임을 입력해야 합니다."),

    //프로필 관련 에러
    PROFILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "PROFILE4001", "프로필을 찾을 수 없습니다."),
    PROFILE_IMAGE_INVALID(HttpStatus.BAD_REQUEST, "PROFILE4002", "잘못된 프로필 이미지입니다."),

    // 학번 관련 에러
    INVALID_STUDENT_NUMBER(HttpStatus.BAD_REQUEST, "STUDENT_NUMBER4001", "숫자만 입력하세요."),

    INVALID_MBTI(HttpStatus.BAD_REQUEST, "MBTI4001", "유효하지 않은 MBTI 값입니다."),

    SUB_MAJOR_NOT_FOUND(HttpStatus.BAD_REQUEST, "MAJOR4001", "유효하지 않은 전공(학과) ID입니다."),

    // 취미 관련 에러 코드
    HOBBY_NOT_FOUND(HttpStatus.BAD_REQUEST, "HOBBY4001", "해당 취미를 찾을 수 없습니다."),
    HOBBY_SELECTION_ERROR(HttpStatus.BAD_REQUEST, "HOBBY4002", "취미 선택에 오류가 발생했습니다. 최대 5개까지 선택 가능합니다."),
    INVALID_HOBBY_ID(HttpStatus.BAD_REQUEST, "HOBBY4003", "유효하지 않은 취미 ID입니다."),

    // 연락 수잔 관련 에러 코드
    CONTACT_TYPE_INVALID(HttpStatus.BAD_REQUEST, "CONTACT4001", "잘못된 연락 수단 입니다."),
    CONTACT_INFO_INVALID(HttpStatus.BAD_REQUEST,"CONTACT4002","잘못된 값 입니다.");
    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
