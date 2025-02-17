package com.umc.meetpick.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.umc.meetpick.enums.ContactType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

public class ProfileDTO {

    // 연락처 설정 DTO
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContactDTO {
        private ContactType contactType;
        private String contactInfo;

        @Getter
        @Builder
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ContactRequestDTO {
            private ContactType contactType;
            private String contactInfo;
        }

        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ContactResponseDTO {
            private Long memberProfileId;
            private ContactType contactType;
            private String contactInfo;
        }
    }

    // 취미 설정 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HobbyDTO {
        private Set<String> hobbyNames;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class HobbyRequestDTO {
            private Set<String> hobbyNames;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class HobbyResponseDTO {
            private Long memberProfileId;
            private Set<String> selectedHobbies;
        }
    }




    // 전공 설정 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MajorDTO {
        private Long subMajorId;


        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MajorRequestDTO {
            private Long subMajorId;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MajorResponseDTO {
            private Long memberId;
            private Long subMajorId;
            private String subMajorName;
            private Long majorId;
            private String majorName;
        }
    }

    // MBTI 설정 DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MBTIDTO {
        private String MBTI;

//        public void setMBTI(String MBTI) {
//            this.MBTI = (MBTI != null) ? MBTI.toUpperCase() : null;
//        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MBTIRequestDTO {

         //   @JsonProperty("MBTI") // JSON 매핑을 강제 적용
            private String MBTI;

            public void setMBTI(String MBTI) {
                this.MBTI = (MBTI != null) ? MBTI.toUpperCase() : null;
            }
        }
        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MBTIResponseDTO {
            private Long memberId;
            private Long memberProfileId;
            private String MBTI;
            private String message;
        }
    }

    // 닉네임 설정 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NicknameDTO {
        private String nickname;

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class NicknameRequestDTO {
            private String nickname;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class NicknameResponseDTO {
            private Long memberId;
            private Long memberProfileId;
            private String nickname;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class NicknameCheckResponseDTO {
            private boolean isAvailable;
        }
    }

    // 프로필 이미지 설정 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileImageDTO {
        private String imageUrl;

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ProfileImageRequestDTO {
            private String imageUrl;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ProfileImageResponseDTO {
            private Long memberId;
            private Long memberProfileId;
            private String profileImage;
        }
    }

    // 학번 설정 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentNumberDTO {
        private String studentNumber;


        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class StudentNumberRequestDTO {

            @NotNull(message = "학번을 입력해야 합니다.")
            @Min(value = 0, message = "숫자만 입력하세요.")
            private String studentNumber;
        }

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class StudentNumberResponseDTO {
            private Long memberId;
            private Long memberProfileId;
            private int studentNumber;
        }
    }
}
