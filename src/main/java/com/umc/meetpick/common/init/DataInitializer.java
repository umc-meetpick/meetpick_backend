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
                        .mateType(MateType.STUDY)
                        .minAge(20)
                        .maxAge(26)
                        .exerciseTypes(Set.of(ExerciseType.RUNNING))
                        .foodTypes(Set.of(FoodType.CHINESE, FoodType.VIETNAMESE))
                        .isSchool(true)
                        .comment("조용하게 공부할 동료를 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile4 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member4)
                        .mateType(MateType.EXERCISE)
                        .minAge(21)
                        .maxAge(28)
                        .exerciseTypes(Set.of(ExerciseType.BOWLING, ExerciseType.RUNNING))
                        .foodTypes(Set.of(FoodType.KOREAN))
                        .isSchool(false)
                        .comment("함께 운동할 친구를 구해요.")
                        .build());

                member3.setMemberSecondProfile(secondProfile3);
                member4.setMemberSecondProfile(secondProfile4);
                memberRepository.save(member3);
                memberRepository.save(member4);

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

// ===== Pair 2 =====
                MemberProfile profile5 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("요리왕")
                        .profileImage("https://example.com/profile5.jpg")
                        .studentNumber(23)
                        // "경제학과"는 socialScience 계열에 있음
                        .subMajor(subMajorRepository.findByNameOrderByName("경제학과"))
                        .MBTI(MBTI.ISTP)
                        .hobbies(Set.of(Hobby.COOKING, Hobby.TRAVELING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("cooking_master")
                        .build());

                MemberProfile profile6 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("댄싱퀸")
                        .profileImage("https://example.com/profile6.jpg")
                        .studentNumber(24)
                        // 역시 "경제학과" 사용 (동일 학과도 가능)
                        .subMajor(subMajorRepository.findByNameOrderByName("경제학과"))
                        .MBTI(MBTI.ESFP)
                        .hobbies(Set.of(Hobby.DANCING, Hobby.WATCHING_MOVIES))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("dancingqueen@example.com")
                        .build());

                Member member5 = memberRepository.save(Member.builder()
                        .name("민수")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1994 - 1900, Calendar.SEPTEMBER, 12))
                        .university(University.KOREA_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(3344556677L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile5)
                        .build());

                Member member6 = memberRepository.save(Member.builder()
                        .name("수진")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1995 - 1900, Calendar.NOVEMBER, 8))
                        .university(University.SOGANG_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(4455667788L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile6)
                        .build());

                MemberSecondProfile secondProfile5 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member5)
                        .mateType(MateType.EXERCISE)
                        .minAge(22)
                        .maxAge(30)
                        .exerciseTypes(Set.of(ExerciseType.RUNNING, ExerciseType.BOWLING))
                        .foodTypes(Set.of(FoodType.JAPANESE, FoodType.CHINESE))
                        .isSchool(true)
                        .comment("함께 운동하고 맛집 탐방할 사람 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile6 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member6)
                        .mateType(MateType.STUDY)
                        .minAge(20)
                        .maxAge(27)
                        .exerciseTypes(Set.of(ExerciseType.BOWLING))
                        .foodTypes(Set.of(FoodType.WESTERN))
                        .isSchool(false)
                        .comment("스터디 모임에 적극 참여할 수 있는 분을 원합니다.")
                        .build());

                member5.setMemberSecondProfile(secondProfile5);
                member6.setMemberSecondProfile(secondProfile6);
                memberRepository.save(member5);
                memberRepository.save(member6);

                MemberSecondProfileMapping mapping5 = memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member5)
                        .memberSecondProfile(secondProfile6)
                        .status(false)
                        .isAccepted(false)
                        .build());

                MemberSecondProfileMapping mapping6 = memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member6)
                        .memberSecondProfile(secondProfile5)
                        .status(true)
                        .isAccepted(true)
                        .build());

                memberMappingRepository.save(mapping5);
                memberMappingRepository.save(mapping6);

// ===== Pair 3 =====
                MemberProfile profile7 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("여행가")
                        .profileImage("https://example.com/profile7.jpg")
                        .studentNumber(25)
                        // "관광학과"는 socialScience 계열에 있음
                        .subMajor(subMajorRepository.findByNameOrderByName("관광학과"))
                        .MBTI(MBTI.ENFP)
                        .hobbies(Set.of(Hobby.TRAVELING, Hobby.PHOTOGRAPHY))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("traveler_kakao")
                        .build());

                MemberProfile profile8 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("음악천재")
                        .profileImage("https://example.com/profile8.jpg")
                        .studentNumber(26)
                        // "의학과"는 medicine 계열에 있음
                        .subMajor(subMajorRepository.findByNameOrderByName("의학과"))
                        .MBTI(MBTI.INTJ)
                        .hobbies(Set.of(Hobby.CRAFTING, Hobby.COLLECTING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("musicgenius@example.com")
                        .build());

                Member member7 = memberRepository.save(Member.builder()
                        .name("동혁")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1993 - 1900, Calendar.MAY, 5))
                        .university(University.HANYANG_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(5566778899L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile7)
                        .build());

                Member member8 = memberRepository.save(Member.builder()
                        .name("지영")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1996 - 1900, Calendar.JULY, 17))
                        .university(University.SUNGKYUNKWAN_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(6677889900L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile8)
                        .build());

                MemberSecondProfile secondProfile7 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member7)
                        .mateType(MateType.STUDY)
                        .minAge(23)
                        .maxAge(29)
                        .exerciseTypes(Set.of(ExerciseType.RUNNING))
                        .foodTypes(Set.of(FoodType.KOREAN))
                        .isSchool(true)
                        .comment("함께 공부하고 여행도 즐길 친구 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile8 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member8)
                        .mateType(MateType.EXERCISE)
                        .minAge(22)
                        .maxAge(28)
                        .exerciseTypes(Set.of(ExerciseType.BOWLING, ExerciseType.RUNNING))
                        .foodTypes(Set.of(FoodType.CHINESE, FoodType.JAPANESE))
                        .isSchool(false)
                        .comment("운동 같이 할 파트너 구함.")
                        .build());

                member7.setMemberSecondProfile(secondProfile7);
                member8.setMemberSecondProfile(secondProfile8);
                memberRepository.save(member7);
                memberRepository.save(member8);

                MemberSecondProfileMapping mapping7 = memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member7)
                        .memberSecondProfile(secondProfile8)
                        .status(false)
                        .isAccepted(false)
                        .build());

                MemberSecondProfileMapping mapping8 = memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member8)
                        .memberSecondProfile(secondProfile7)
                        .status(true)
                        .isAccepted(true)
                        .build());

                memberMappingRepository.save(mapping7);
                memberMappingRepository.save(mapping8);

// ===== Pair 4 =====
                MemberProfile profile9 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("코딩천재")
                        .profileImage("https://example.com/profile9.jpg")
                        .studentNumber(27)
                        // "컴퓨터공학과"는 위 목록에 없으므로, engineering 계열의 "전기전자공학과"로 대체
                        .subMajor(subMajorRepository.findByNameOrderByName("전기전자공학과"))
                        .MBTI(MBTI.ENTP)
                        .hobbies(Set.of(Hobby.YOGA, Hobby.READING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("codegenius")
                        .build());

                MemberProfile profile10 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("디자인여신")
                        .profileImage("https://example.com/profile10.jpg")
                        .studentNumber(28)
                        // "디자인학과"는 없으므로, artsAndPhysical 계열의 "영상학과"로 대체
                        .subMajor(subMajorRepository.findByNameOrderByName("영상학과"))
                        .MBTI(MBTI.ISFJ)
                        .hobbies(Set.of(Hobby.SINGING, Hobby.DANCING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("designgoddess@example.com")
                        .build());

                Member member9 = memberRepository.save(Member.builder()
                        .name("준호")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1995 - 1900, Calendar.AUGUST, 23))
                        .university(University.DONGGUK_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(7788990011L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile9)
                        .build());

                Member member10 = memberRepository.save(Member.builder()
                        .name("수빈")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1997 - 1900, Calendar.FEBRUARY, 14))
                        .university(University.HONGIK_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(8899001122L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile10)
                        .build());

                MemberSecondProfile secondProfile9 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member9)
                        .mateType(MateType.STUDY)
                        .minAge(24)
                        .maxAge(30)
                        .exerciseTypes(Set.of(ExerciseType.RUNNING))
                        .foodTypes(Set.of(FoodType.WESTERN))
                        .isSchool(true)
                        .comment("코딩 스터디에 관심 있는 분 환영합니다.")
                        .build());

                MemberSecondProfile secondProfile10 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member10)
                        .mateType(MateType.EXERCISE)
                        .minAge(23)
                        .maxAge(29)
                        .exerciseTypes(Set.of(ExerciseType.BOWLING))
                        .foodTypes(Set.of(FoodType.KOREAN, FoodType.JAPANESE))
                        .isSchool(false)
                        .comment("함께 운동하면서 디자인 영감을 나눌 분 찾습니다.")
                        .build());

                member9.setMemberSecondProfile(secondProfile9);
                member10.setMemberSecondProfile(secondProfile10);
                memberRepository.save(member9);
                memberRepository.save(member10);

                MemberSecondProfileMapping mapping9 = memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member9)
                        .memberSecondProfile(secondProfile10)
                        .status(false)
                        .isAccepted(false)
                        .build());

                MemberSecondProfileMapping mapping10 = memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member10)
                        .memberSecondProfile(secondProfile9)
                        .status(true)
                        .isAccepted(true)
                        .build());

                memberMappingRepository.save(mapping9);
                memberMappingRepository.save(mapping10);

// ===== Pair 5 =====
                MemberProfile profile11 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("비즈니스킹")
                        .profileImage("https://example.com/profile11.jpg")
                        .studentNumber(29)
                        // "경영학과"는 socialScience 계열에 있음
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.ESTJ)
                        .hobbies(Set.of(Hobby.COLLECTING, Hobby.GARDENING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("bizking")
                        .build());

                MemberProfile profile12 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("예술혼")
                        .profileImage("https://example.com/profile12.jpg")
                        .studentNumber(30)
                        // "미술학과"는 없으므로, artsAndPhysical 계열의 "문예창작학과"로 대체
                        .subMajor(subMajorRepository.findByNameOrderByName("문예창작학과"))
                        .MBTI(MBTI.INFP)
                        .hobbies(Set.of(Hobby.CRAFTING, Hobby.WRITING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("artsoul@example.com")
                        .build());

                Member member11 = memberRepository.save(Member.builder()
                        .name("영수")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1992 - 1900, Calendar.OCTOBER, 30))
                        .university(University.KYUNGHEE_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(9900112233L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile11)
                        .build());

                Member member12 = memberRepository.save(Member.builder()
                        .name("민지")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1994 - 1900, Calendar.MAY, 25))
                        .university(University.SUNGSHIN_WOMANS_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(1100220033L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile12)
                        .build());

                MemberSecondProfile secondProfile11 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member11)
                        .mateType(MateType.STUDY)
                        .minAge(25)
                        .maxAge(35)
                        .exerciseTypes(Set.of(ExerciseType.BOWLING))
                        .foodTypes(Set.of(FoodType.WESTERN))
                        .isSchool(true)
                        .comment("경영 관련 스터디 모임에 관심 있으신 분들 환영합니다.")
                        .build());

                MemberSecondProfile secondProfile12 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member12)
                        .mateType(MateType.EXERCISE)
                        .minAge(23)
                        .maxAge(32)
                        .exerciseTypes(Set.of(ExerciseType.RUNNING))
                        .foodTypes(Set.of(FoodType.KOREAN))
                        .isSchool(false)
                        .comment("예술적 감성을 가진 운동 파트너 찾습니다.")
                        .build());

                member11.setMemberSecondProfile(secondProfile11);
                member12.setMemberSecondProfile(secondProfile12);
                memberRepository.save(member11);
                memberRepository.save(member12);

                MemberSecondProfileMapping mapping11 = memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member11)
                        .memberSecondProfile(secondProfile12)
                        .status(false)
                        .isAccepted(false)
                        .build());

                MemberSecondProfileMapping mapping12 = memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member12)
                        .memberSecondProfile(secondProfile11)
                        .status(true)
                        .isAccepted(true)
                        .build());

                memberMappingRepository.save(mapping11);
                memberMappingRepository.save(mapping12);

            }

        }

    }
}

