package com.umc.meetpick.dto;

import com.umc.meetpick.enums.ContactType;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.Hobby;
import com.umc.meetpick.enums.MBTI;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class RegisterDTO {

    // SignUpDTO
    @Getter
    @Setter
    @Builder
    public static class SignUpDTO {

        @Size(min = 1, max = 4)
        private String name;

        @Pattern(regexp = "남성|여성", message = "성별은 '남성' 또는 '여성'만 가능합니다.")
        private String gender;

        @NotNull(message = "생년월일은 필수 입력 값입니다.")
        @Past(message = "생년월일은 과거 날짜만 가능합니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
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
