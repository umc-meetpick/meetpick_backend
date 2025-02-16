package com.umc.meetpick.common.init;

import com.umc.meetpick.entity.*;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.enums.*;
import com.umc.meetpick.repository.*;
import com.umc.meetpick.repository.member.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final MajorRepository majorRepository;
    private final SubMajorRepository subMajorRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final MemberRepository memberRepository;
    private final MemberSecondProfileRepository memberSecondProfileRepository;
    //private final PersonalityRepository personalityRepository;
    private final MemberMappingRepository memberMappingRepository;

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

        // 사용자 기본값 저장
        if (memberRepository.count() == 0) {
            if (memberRepository.count() == 0) {

// ===== Pair 1 =====
                MemberProfile profile3 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("책벌레")
                        .profileImage("https://example.com/profile3.jpg")
                        .studentNumber(21)
                        // "윤리교육과"는 socialScience 계열에 있음
                        .subMajor(subMajorRepository.findByNameOrderByName("윤리교육과"))
                        .MBTI(MBTI.INFJ)
                        .hobbies(Set.of(Hobby.READING, Hobby.WRITING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("bookworm_kakao")
                        .build());

                MemberProfile profile4 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("스포츠킹")
                        .profileImage("https://example.com/profile4.jpg")
                        .studentNumber(22)
                        // "국어국문학과"는 humanities 계열에 있음
                        .subMajor(subMajorRepository.findByNameOrderByName("국어국문학과"))
                        .MBTI(MBTI.ESTP)
                        .hobbies(Set.of(Hobby.SPORTS, Hobby.GARDENING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("sportsking@example.com")
                        .build());

                Member member3 = memberRepository.save(Member.builder()
                        .name("철수")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1996 - 1900, Calendar.DECEMBER, 5))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(1122334455L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile3)
                        .build());

                Member member4 = memberRepository.save(Member.builder()
                        .name("영희")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1997 - 1900, Calendar.JANUARY, 20))
                        .university(University.EWHA_WOMANS_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(2233445566L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile4)
                        .build());

                MemberSecondProfile secondProfile3 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member3)
                        .mateType(MateType.MEAL)
                        .minAge(20)
                        .maxAge(26)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.CHINESE, FoodType.VIETNAMESE))
                        .isSchool(true)
                        .comment("조용하게 공부할 동료를 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile4 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member4)
                        .mateType(MateType.MEAL)
                        .minAge(21)
                        .maxAge(28)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.KOREAN))
                        .isSchool(false)
                        .comment("함께 운동할 친구를 구해요.")
                        .build());

                secondProfile3.setMember(member3);
                secondProfile4.setMember(member4);
                memberSecondProfileRepository.save(secondProfile3);
                memberSecondProfileRepository.save(secondProfile4);

                MemberSecondProfileMapping mapping3 = memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member3)
                        .memberSecondProfile(secondProfile4)
                        .status(false)
                        .isAccepted(false)
                        .build());

                MemberSecondProfileMapping mapping4 = memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member4)
                        .memberSecondProfile(secondProfile3)
                        .status(true)
                        .isAccepted(true)
                        .build());

                memberMappingRepository.save(mapping3);
                memberMappingRepository.save(mapping4);
            }

        }

    }
}

