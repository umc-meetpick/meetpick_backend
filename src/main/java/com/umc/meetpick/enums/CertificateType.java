package com.umc.meetpick.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CertificateType {
    LANGUAGE("어학"),
    EMPLOYMENT("취업"),
    CIVIL_SERVICE("고시/공무원"),
    SOCIAL_SCIENCE("사회과학계열"),
    HOBBY("취미/교양"),
    PROGRAMMING("프로그래밍");

    private final String koreanName;

    CertificateType(String koreanName) {
        this.koreanName = koreanName;
    }


    // json직렬화/역직렬화 추가
    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }

    // fromString 메소드 추가
    public static CertificateType fromString(String type) {
        for (CertificateType certificateType : CertificateType.values()) {
            if (certificateType.koreanName.equalsIgnoreCase(type)) {
                return certificateType;
            }
        }
        throw new IllegalArgumentException("Invalid certificate type: " + type);
    }
}