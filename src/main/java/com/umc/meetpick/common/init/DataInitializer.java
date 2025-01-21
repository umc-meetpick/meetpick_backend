package com.umc.meetpick.common.init;

import com.umc.meetpick.entity.*;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.MemberRole;
import com.umc.meetpick.enums.MemberStatus;
import com.umc.meetpick.enums.SocialType;
import com.umc.meetpick.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final MajorRepository majorRepository;
    private final SubMajorRepository subMajorRepository;
    private final UniversityRepository universityRepository;
    private final MemberRepository memberRepository;
    private final JdbcTemplate jdbcTemplate;
    private final RequestRepository requestRepository;

    /*@PostConstruct
    public void init() {
        // FULLTEXT INDEX 생성 쿼리 실행
        String createIndexQuery = "CREATE FULLTEXT INDEX university_index ON university(universityName) WITH PARSER ngram(1)";
        jdbcTemplate.execute(createIndexQuery);
    } mysql 사용 시 사용하기 */

    @Override
    public void run(String... args) throws Exception {

        // Major(전공) 저장
        Major engineering = majorRepository.findById(1L).orElse(majorRepository.save(new Major("공학 계열")));
        Major science = majorRepository.findById(2L).orElse(majorRepository.save(new Major("자연과학 계열")));
        Major humanities = majorRepository.findById(3L).orElse(majorRepository.save(new Major("인문학 계열")));
        Major socialScience = majorRepository.findById(4L).orElse(majorRepository.save(new Major("사회과학 계열")));
        Major medicine = majorRepository.findById(5L).orElse(majorRepository.save(new Major("의학 계열")));
        Major artsAndPhysical = majorRepository.findById(6L).orElse(majorRepository.save(new Major("예술·체육 계열")));
        Major agricultureAndLife = majorRepository.findById(7L).orElse(majorRepository.save(new Major("농·생명 계열")));
        Major convergenceAndSpecialization = majorRepository.findById(8L).orElse(majorRepository.save(new Major("융합/특성화 계열")));

        // 사용자 기본값 저장
        if (memberRepository.count() == 0) {
            memberRepository.save(Member.builder()
                    .gender(Gender.MALE)
                    .birthday(new java.util.Date(1995, 6, 15))
                    .university("서울대학교")
                    .socialType(SocialType.KAKAO)
                    .socialId(1234567890L)
                    .status(MemberStatus.ACTIVE)
                    .role(MemberRole.MEMBER)
                    .build());

            memberRepository.save(Member.builder()
                    .gender(Gender.FEMALE)
                    .birthday(new java.util.Date(1998, 4, 20))
                    .university("연세대학교")
                    .socialType(SocialType.KAKAO)
                    .socialId(9876543210L)
                    .status(MemberStatus.ACTIVE)
                    .role(MemberRole.MEMBER)
                    .build());

            memberRepository.save(Member.builder()
                    .gender(Gender.MALE)
                    .birthday(new java.util.Date(2000, 11, 10))
                    .university("고려대학교")
                    .socialType(SocialType.KAKAO)
                    .socialId(1122334455L)
                    .status(MemberStatus.ACTIVE)
                    .role(MemberRole.MEMBER)
                    .build());
        }

        if (subMajorRepository.count() == 0) {
            // SubMajor(전공 학과) 저장
            subMajorRepository.save(new SubMajor("건설방재공학과", engineering));
            subMajorRepository.save(new SubMajor("건축공학과", engineering));
            subMajorRepository.save(new SubMajor("건축학과", engineering));
            subMajorRepository.save(new SubMajor("기계공학과", engineering));
            subMajorRepository.save(new SubMajor("산업공학과", engineering));
            subMajorRepository.save(new SubMajor("섬유공학과", engineering));
            subMajorRepository.save(new SubMajor("안전공학과", engineering));
            subMajorRepository.save(new SubMajor("에너지공학과", engineering));
            subMajorRepository.save(new SubMajor("전기전자공학과", engineering));
            subMajorRepository.save(new SubMajor("정보통신공학과", engineering));
            subMajorRepository.save(new SubMajor("재료공학과", engineering));
            subMajorRepository.save(new SubMajor("철도공학과", engineering));
            subMajorRepository.save(new SubMajor("화학공학과", engineering));

            subMajorRepository.save(new SubMajor("물리학과", science));
            subMajorRepository.save(new SubMajor("생명과학과", science));
            subMajorRepository.save(new SubMajor("수학과", science));
            subMajorRepository.save(new SubMajor("지리학과", science));
            subMajorRepository.save(new SubMajor("화학과", science));
            subMajorRepository.save(new SubMajor("에너지공학과", science));
            subMajorRepository.save(new SubMajor("환경공학과", science));

            subMajorRepository.save(new SubMajor("국어국문학과", humanities));
            subMajorRepository.save(new SubMajor("노어노문학과", humanities));
            subMajorRepository.save(new SubMajor("독어독문학과", humanities));
            subMajorRepository.save(new SubMajor("불어불문학과", humanities));
            subMajorRepository.save(new SubMajor("서어서문학과", humanities));
            subMajorRepository.save(new SubMajor("영어영문학과", humanities));
            subMajorRepository.save(new SubMajor("일어일문학과", humanities));
            subMajorRepository.save(new SubMajor("중어중문학과", humanities));
            subMajorRepository.save(new SubMajor("철학과", humanities));
            subMajorRepository.save(new SubMajor("문헌정보학과", humanities));
            subMajorRepository.save(new SubMajor("역사학과(사학과)", humanities));

            subMajorRepository.save(new SubMajor("경영정보학과", socialScience));
            subMajorRepository.save(new SubMajor("경영학과", socialScience));
            subMajorRepository.save(new SubMajor("경제학과", socialScience));
            subMajorRepository.save(new SubMajor("경찰학과", socialScience));
            subMajorRepository.save(new SubMajor("관광학과", socialScience));
            subMajorRepository.save(new SubMajor("사회학과", socialScience));
            subMajorRepository.save(new SubMajor("사회복지학과", socialScience));
            subMajorRepository.save(new SubMajor("정치외교학과", socialScience));
            subMajorRepository.save(new SubMajor("행정학과", socialScience));
            subMajorRepository.save(new SubMajor("심리학과", socialScience));
            subMajorRepository.save(new SubMajor("교육학과", socialScience));
            subMajorRepository.save(new SubMajor("특수교육과", socialScience));
            subMajorRepository.save(new SubMajor("일반사회교육과", socialScience));
            subMajorRepository.save(new SubMajor("윤리교육과", socialScience));
            subMajorRepository.save(new SubMajor("지리교육과", socialScience));

            subMajorRepository.save(new SubMajor("간호학과", medicine));
            subMajorRepository.save(new SubMajor("물리치료학과", medicine));
            subMajorRepository.save(new SubMajor("수의학과", medicine));
            subMajorRepository.save(new SubMajor("약학과", medicine));
            subMajorRepository.save(new SubMajor("임상병리학과", medicine));
            subMajorRepository.save(new SubMajor("치의학과", medicine));
            subMajorRepository.save(new SubMajor("치위생학과", medicine));
            subMajorRepository.save(new SubMajor("한약학과", medicine));
            subMajorRepository.save(new SubMajor("한의학과", medicine));
            subMajorRepository.save(new SubMajor("의학과", medicine));

            subMajorRepository.save(new SubMajor("게임학과", artsAndPhysical));
            subMajorRepository.save(new SubMajor("문예창작학과", artsAndPhysical));
            subMajorRepository.save(new SubMajor("연극학과", artsAndPhysical));
            subMajorRepository.save(new SubMajor("영상학과", artsAndPhysical));
            subMajorRepository.save(new SubMajor("조리과학과", artsAndPhysical));
            subMajorRepository.save(new SubMajor("제과제빵과", artsAndPhysical));
            subMajorRepository.save(new SubMajor("커뮤니케이션학과", artsAndPhysical));

            subMajorRepository.save(new SubMajor("농업경제학과", agricultureAndLife));
            subMajorRepository.save(new SubMajor("농업자원경제학과", agricultureAndLife));
            subMajorRepository.save(new SubMajor("동물자원학과", agricultureAndLife));
            subMajorRepository.save(new SubMajor("조경학과", agricultureAndLife));
            subMajorRepository.save(new SubMajor("식품영양학과", agricultureAndLife));
            subMajorRepository.save(new SubMajor("수산생명의학과", agricultureAndLife));
            subMajorRepository.save(new SubMajor("환경생태학과", agricultureAndLife));

            subMajorRepository.save(new SubMajor("인공지능학과", convergenceAndSpecialization));
            subMajorRepository.save(new SubMajor("정보보안학과", convergenceAndSpecialization));
            subMajorRepository.save(new SubMajor("자유전공학부", convergenceAndSpecialization));
            subMajorRepository.save(new SubMajor("특성화 학과", convergenceAndSpecialization));
        }

        if (universityRepository.count() == 0) {
            universityRepository.save(new University("서울대학교", "서울특별시 관악구 관악로 1"));
            universityRepository.save(new University("고려대학교", "서울특별시 성북구 안암로 145"));
            universityRepository.save(new University("연세대학교", "서울특별시 서대문구 연세로 50"));
            universityRepository.save(new University("한양대학교", "서울특별시 성동구 왕십리로 222"));
            universityRepository.save(new University("성균관대학교", "서울특별시 종로구 성균관로 25-2"));
            universityRepository.save(new University("서강대학교", "서울특별시 마포구 백범로 35"));
            universityRepository.save(new University("중앙대학교", "서울특별시 동작구 흑석로 84"));
            universityRepository.save(new University("경희대학교", "서울특별시 동대문구 경희대로 26"));
            universityRepository.save(new University("이화여자대학교", "서울특별시 서대문구 이화여대길 52"));
            universityRepository.save(new University("한국외국어대학교", "서울특별시 동대문구 이문로 107"));
            universityRepository.save(new University("서울시립대학교", "서울특별시 동대문구 서울시립대로 163"));
            universityRepository.save(new University("건국대학교", "서울특별시 광진구 능동로 120"));
            universityRepository.save(new University("동국대학교", "서울특별시 중구 필동로 1길 30"));
            universityRepository.save(new University("홍익대학교", "서울특별시 마포구 와우산로 94"));
            universityRepository.save(new University("세종대학교", "서울특별시 광진구 능동로 209"));
            universityRepository.save(new University("숭실대학교", "서울특별시 동작구 상도로 369"));
            universityRepository.save(new University("한성대학교", "서울특별시 성북구 삼선교로 16"));
            universityRepository.save(new University("서울과학기술대학교", "서울특별시 노원구 공릉로 232"));
            universityRepository.save(new University("서울시립대학교", "서울특별시 동대문구 서울시립대로 163"));
            universityRepository.save(new University("한국항공대학교", "서울특별시 강서구 화곡로 76"));
            universityRepository.save(new University("덕성여자대학교", "서울특별시 도봉구 덕성로 132"));
        }

    }
}

