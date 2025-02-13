package com.umc.meetpick.enums;

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
}