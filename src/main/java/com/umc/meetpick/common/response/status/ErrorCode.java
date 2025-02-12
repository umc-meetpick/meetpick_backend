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

    // 멤버 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    MEMBER_LOGIN_FAILURE(HttpStatus.BAD_REQUEST, "MEMBER4003", "아이디 혹은 비밀번호를 잘못 입력하였습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),
    NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "MEMBER4003", "존재하는 닉네임입니다"),
    SUBMAJOR_NOT_EXSIT(HttpStatus.BAD_REQUEST, "MEMBER4004", "존재하지 않는 전공입니다"),

    MEMBER_SIGNUP_ERROR(HttpStatus.BAD_REQUEST, "SIGNUP4001", "회원가입 유효성 검사 실패"),
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "SIGNUP4002", "이미 존재하는 이메일입니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "SIGNUP4003", "이메일 인증을 하지 않은 유저입니다."),

    POST_NOTFOUND(HttpStatus.BAD_REQUEST, "POST4004", "게시물을 찾을 수 없습니다."),

    UNSIGNED(HttpStatus.BAD_REQUEST, "POST4001", "로그인 되어 있지 않습니다."),

    INVALID_UNIVERSITY(HttpStatus.BAD_REQUEST, "UNIV4001" ,"대학교명 형식 오류: 예) xx대학교와 같이 입력하세요."),

    //enum 관련
    INVALID_ENUM(HttpStatus.BAD_REQUEST, "ENUM4001" ,"존재하지 않는 ENUM입니다.")


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
