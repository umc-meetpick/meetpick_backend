package com.umc.meetpick.enums;

public enum University {
    SEOUL_NATIONAL_UNIVERSITY("서울대학교", "서울특별시 관악구 관악로 1"),
    KOREA_UNIVERSITY("고려대학교", "서울특별시 성북구 안암로 145"),
    YONSEI_UNIVERSITY("연세대학교", "서울특별시 서대문구 연세로 50"),
    HANYANG_UNIVERSITY("한양대학교", "서울특별시 성동구 왕십리로 222"),
    SUNGKYUNKWAN_UNIVERSITY("성균관대학교", "서울특별시 종로구 성균관로 25-2"),
    SOGANG_UNIVERSITY("서강대학교", "서울특별시 마포구 백범로 35"),
    CHUNGANG_UNIVERSITY("중앙대학교", "서울특별시 동작구 흑석로 84"),
    KYUNGHEE_UNIVERSITY("경희대학교", "서울특별시 동대문구 경희대로 26"),
    EWHA_WOMANS_UNIVERSITY("이화여자대학교", "서울특별시 서대문구 이화여대길 52"),
    HANKUK_UNIVERSITY("한국외국어대학교", "서울특별시 동대문구 이문로 107"),
    CITY_UNIVERSITY("서울시립대학교", "서울특별시 동대문구 서울시립대로 163"),
    KUNKUK_UNIVERSITY("건국대학교", "서울특별시 광진구 능동로 120"),
    DONGGUK_UNIVERSITY("동국대학교", "서울특별시 중구 필동로 1길 30"),
    HONGIK_UNIVERSITY("홍익대학교", "서울특별시 마포구 와우산로 94"),
    SEJONG_UNIVERSITY("세종대학교", "서울특별시 광진구 능동로 209"),
    SOONGSHIL_UNIVERSITY("숭실대학교", "서울특별시 동작구 상도로 369"),
    HANSEONG_UNIVERSITY("한성대학교", "서울특별시 성북구 삼선교로 16"),
    SEOUL_NATIONAL_SCIENCE_TECHNOLOGY("서울과학기술대학교", "서울특별시 노원구 공릉로 232"),
    KOREAN_AIR_UNIVERSITY("한국항공대학교", "서울특별시 강서구 화곡로 76"),
    DEOKSEONG_WOMANS_UNIVERSITY("덕성여자대학교", "서울특별시 도봉구 덕성로 132");

    private final String universityName;
    private final String address;

    // 생성자
    University(String universityName, String address) {
        this.universityName = universityName;
        this.address = address;
    }

    // getter 메서드
    public String getUniversityName() {
        return universityName;
    }

    public String getAddress() {
        return address;
    }
}

