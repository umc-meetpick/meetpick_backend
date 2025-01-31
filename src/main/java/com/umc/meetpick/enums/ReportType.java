package com.umc.meetpick.enums;

public enum ReportType {
    INCORRECT_INFORMATION("기재된 정보랑 달라요"),
    NO_CONTACT_AFTER_MATCH("매칭 후 연락이 없어요"),
    ISSUE_DURING_MEETING("만남에서 문제가 발생했어요"),
    OTHER_ISSUES("그 외 다른 문제가 있어요");

    private final String koreanName;

    ReportType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static ReportType fromKoreanName(String koreanName) {
        for (ReportType type : ReportType.values()) {
            if (type.koreanName.equals(koreanName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown ReportType: " + koreanName);
    }
}
