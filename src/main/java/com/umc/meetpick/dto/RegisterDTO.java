package com.umc.meetpick.dto;

import com.umc.meetpick.enums.ContactType;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.Hobby;
import com.umc.meetpick.enums.MBTI;
import lombok.*;

import java.util.Date;
import java.util.List;

public class RegisterDTO {

    // SignUpDTO
    @Getter
    @Setter
    @Builder
    public static class SignUpDTO {
        private String name;
        private Gender gender;
        private Date birthday;
    }

    // EmailVerificationCodeDTO
    @Getter
    @Setter
    @NoArgsConstructor
    public static class EmailVerificationCodeDTO {
        private String email;
        private String univName;
        private int verificationCode;
    }

    // EmailVerificationRequestDTO
    @Getter
    @Setter
    @NoArgsConstructor
    public static class EmailVerificationRequestDTO {
        private String email;
        private String univName;
    }

    // SignUpDTO
    @Getter
    @Setter
    @Builder
    public static class SignUpProfileDTO {
        private String name;
        //TODO 나중에 enum으로 다르게 관리하기
        private int imageNumber;
        private int studentNumber;
        private MBTI mbti;
        private String SubMajor;
        private List<Hobby> hobbyList;
        private ContactType contactType;

        //TODO 유효성 검사 추가
        private String contactInfo;
    }

    // SignupSuccessDTO
    @Getter
    @AllArgsConstructor
    @Builder
    public static class SignupSuccessDTO {
        private Long memberId;
    }

    // TermsDTO
    @Getter
    @Setter
    @AllArgsConstructor
    public static class TermsDTO {
        private Long memberId;
        private boolean termsAgreed;
    }
}
