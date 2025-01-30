package com.umc.meetpick.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor // 역직렬화를 위한 기본 생성자
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponseDTO {

    @JsonProperty("id")  // Kakao의 회원 고유 ID
    private Long socialId;

    @JsonProperty("connected_at")
    private Date connectedAt;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {

        @JsonProperty("profile")
        private Profile profile;

        @JsonProperty("gender")
        private String gender;

        @JsonProperty("birthday")
        private String birthday;

        @Getter
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Profile {

            @JsonProperty("nickname")
            private String nickName;
        }
    }

    // ✅ socialId를 가져오는 Getter 추가
    public Long getSocialId() {
        return this.socialId;
    }

    // ✅ 닉네임 가져오는 Getter 추가
    public String getNickname() {
        return this.kakaoAccount != null && this.kakaoAccount.profile != null
                ? this.kakaoAccount.profile.nickName
                : null;
    }

    // ✅ 성별 가져오는 Getter 추가
    public String getGender() {
        return this.kakaoAccount != null ? this.kakaoAccount.gender : null;
    }

    // ✅ 생일 가져오는 Getter 추가
    public String getBirthday() {
        return this.kakaoAccount != null ? this.kakaoAccount.birthday : null;
    }
}
