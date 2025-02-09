package com.umc.meetpick.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum University {

    //TODO 진짜 있는건지 확인 필요
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
    DEOKSEONG_WOMANS_UNIVERSITY("덕성여자대학교", "서울특별시 도봉구 덕성로 132"),
    MYONGJI_UNIVERSITY("명지대학교", "서울특별시 서대문구 거북골로 34"),
    SEOUL_WOMANS_UNIVERSITY("서울여자대학교", "서울특별시 노원구 화랑로 621"),
    DUKSUNG_WOMANS_UNIVERSITY("덕성여자대학교", "서울특별시 도봉구 덕성로 76"),
    SUNGSHIN_WOMANS_UNIVERSITY("성신여자대학교", "서울특별시 성북구 보문로34다길 2"),
    SOOKMYUNG_WOMANS_UNIVERSITY("숙명여자대학교", "서울특별시 용산구 청파로47길 100"),
    HANYANG_WOMANS_UNIVERSITY("한양여자대학교", "서울특별시 성동구 왕십리로 222"),
    KOREA_NATIONAL_OPEN_UNIVERSITY("한국방송통신대학교", "서울특별시 종로구 대학로 86"),
    SEOUL_CHRISTIAN_UNIVERSITY("서울기독대학교", "서울특별시 은평구 갈현로 5"),
    SEOUL_JANGSIN_UNIVERSITY("서울장신대학교", "서울특별시 강북구 오현로 77"),
    KOREA_THEOLOGICAL_SEMINARY("한국신학대학교", "서울특별시 서초구 반포대로 56"),
    SAMYOOK_UNIVERSITY("삼육대학교", "서울특별시 노원구 화랑로 815"),
    KYUNGIN_UNIVERSITY("경인교육대학교", "서울특별시 종로구 대학로 1"),
    SEOUL_ART_UNIVERSITY("서울예술대학교", "서울특별시 중구 필동로 1길 13"),
    KOREA_THEOLOGICAL_UNIVERSITY("한국신학대학교", "서울특별시 서초구 남부순환로 220"),
    SEOUL_PHYSICAL_EDUCATION_UNIVERSITY("서울체육대학교", "서울특별시 송파구 올림픽로 424"),
    SEOUL_MEDICAL_SCIENCE_UNIVERSITY("서울보건대학교", "서울특별시 강동구 강동대로 110"),
    KYUNGIN_WOMANS_UNIVERSITY("경인여자대학교", "서울특별시 강서구 화곡로 150"),
    BAIKSEOK_WOMANS_UNIVERSITY("백석여자대학교", "서울특별시 강남구 도산대로 535"),
    CHUNGGYE_WOMANS_UNIVERSITY("청계여자대학교", "서울특별시 종로구 청계천로 45");



    private final String universityName;
    private final String address;

    // 생성자
    University(String universityName, String address) {
        this.universityName = universityName;
        this.address = address;
    }

    public static University fromString(String universityName) {
        for (University university : University.values()) {
            if (university.universityName.equalsIgnoreCase(universityName.trim())) {
                return university;
            }
        }
        return SEOUL_NATIONAL_UNIVERSITY; // ✅ 존재하지 않는 경우 안전하게 기본값 반환
    }

    public String getKoreanName() {
        return this.universityName;
    }

    // 검색 후 일치하는 목록 반환
    // TODO 나중에 리팩토링
    public static List<Map<String, String>> search(String keyword) {
        List<Map<String, String>> result = new ArrayList<>();
        for (University university : University.values()) {
            if (university.universityName.contains(keyword)) {
                Map<String, String> map = new HashMap<>();
                map.put("universityName", university.universityName);
                map.put("address", university.address);
                result.add(map);
            }
        }
        return result;
    }

}

