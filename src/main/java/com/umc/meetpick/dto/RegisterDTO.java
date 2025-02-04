package com.umc.meetpick.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RegisterDTO {

    // SignUpDTO
    @Getter
    @Setter
    @Builder
    public static class SignUpDTO {
        private Long memberId;
        private String name;
        private String gender;
        private String birthday;
    }

    // EmailVerificationCodeDTO
    @Getter
    @NoArgsConstructor
    public static class EmailVerificationCodeDTO {
        private Long memberId;
        private String email;
        private String univName;
        private int verificationCode;
    }

    // EmailVerificationRequestDTO
    @Getter
    @NoArgsConstructor
    public static class EmailVerificationRequestDTO {
        private Long memberId;
        private String email;
        private String univName;
    }

    // SignupSuccessDTO
    @Getter
    @AllArgsConstructor
    public static class SignupSuccessDTO {
        private Long memberId;
        private String message;
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
