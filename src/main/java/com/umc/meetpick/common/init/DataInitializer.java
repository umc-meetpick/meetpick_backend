package com.umc.meetpick.common.init;

import com.umc.meetpick.entity.*;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.enums.*;
import com.umc.meetpick.repository.*;
import com.umc.meetpick.repository.member.*;
import com.umc.meetpick.service.matching.processor.MatchingDataProcessor;
import com.umc.meetpick.service.matching.processor.MemberDataProcessor;
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
    private final MemberDataProcessor memberDataProcessor;
    private final MatchingDataProcessor matchingDataProcessor;
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
                        .nickname("밋픽의 왕")
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
                        .name("밋픽의 왕")
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
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(2233445566L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile4)
                        .build());

                MemberSecondProfile secondProfile3 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member3)
                        .mateType(MateType.MEAL)
                        .gender(Gender.FEMALE)
                        .mbti("ENFP")
                        .minAge(20)
                        .maxAge(22)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.KOREAN, FoodType.VIETNAMESE))
                        .isSchool(true)
                        .comment("조용하게 공부할 동료를 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile4 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member4)
                        .mbti("INFJ")
                        .mateType(MateType.MEAL)
                        .gender(Gender.MALE)
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

                memberDataProcessor.process(secondProfile3);
                matchingDataProcessor.process(secondProfile3);
                memberDataProcessor.process(secondProfile4);
                matchingDataProcessor.process(secondProfile4);

                // ===== Pair 2 =====
                MemberProfile profile5 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("책읽는민수")
                        .profileImage("https://example.com/profile5.jpg")
                        .studentNumber(23)
                        .subMajor(subMajorRepository.findByNameOrderByName("철학과"))
                        .MBTI(MBTI.INTJ)
                        .hobbies(Set.of(Hobby.READING, Hobby.PHOTOGRAPHY))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("minsookakao")
                        .build());

                MemberProfile profile6 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("활발한지영")
                        .profileImage("https://example.com/profile6.jpg")
                        .studentNumber(24)
                        .subMajor(subMajorRepository.findByNameOrderByName("심리학과"))
                        .MBTI(MBTI.ENTP)
                        .hobbies(Set.of(Hobby.TRAVELING, Hobby.DANCING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("jiyoung@example.com")
                        .build());

                Member member5 = memberRepository.save(Member.builder()
                        .name("민수")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1995 - 1900, Calendar.NOVEMBER, 10))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(3344556677L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile5)
                        .build());

                Member member6 = memberRepository.save(Member.builder()
                        .name("지영")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1996 - 1900, Calendar.FEBRUARY, 14))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(4455667788L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile6)
                        .build());

                MemberSecondProfile secondProfile5 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member5)
                        .mateType(MateType.MEAL)
                        .gender(Gender.MALE)
                        .mbti("INTJ")
                        .minAge(22)
                        .maxAge(28)
                        .maxPeople(4)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.CHINESE, FoodType.KOREAN))
                        .isSchool(true)
                        .comment("함께 공부할 파트너를 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile6 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member6)
                        .mateType(MateType.MEAL)
                        .gender(Gender.FEMALE)
                        .mbti("ENTP")
                        .minAge(21)
                        .maxAge(27)
                        .maxPeople(4)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.KOREAN))
                        .isSchool(false)
                        .comment("열정적인 스터디 그룹을 원합니다.")
                        .build());

                secondProfile5.setMember(member5);
                secondProfile6.setMember(member6);
                memberSecondProfileRepository.save(secondProfile5);
                memberSecondProfileRepository.save(secondProfile6);

                memberDataProcessor.process(secondProfile5);
                matchingDataProcessor.process(secondProfile5);
                memberDataProcessor.process(secondProfile6);
                matchingDataProcessor.process(secondProfile6);

// ===== Pair 3 =====
                MemberProfile profile7 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("요리왕준호")
                        .profileImage("https://example.com/profile7.jpg")
                        .studentNumber(25)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.ENTJ)
                        .hobbies(Set.of(Hobby.COOKING, Hobby.BAKING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("junho@example.com")
                        .build());

                MemberProfile profile8 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("예술가수진")
                        .profileImage("https://example.com/profile8.jpg")
                        .studentNumber(26)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.INFP)
                        .hobbies(Set.of(Hobby.PAINTING, Hobby.SINGING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("sujin_kakao")
                        .build());

                Member member7 = memberRepository.save(Member.builder()
                        .name("준호")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1994 - 1900, Calendar.MARCH, 15))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(5566778899L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile7)
                        .build());

                Member member8 = memberRepository.save(Member.builder()
                        .name("수진")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1995 - 1900, Calendar.APRIL, 20))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(6677889900L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile8)
                        .build());

                MemberSecondProfile secondProfile7 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member7)
                        .mateType(MateType.MEAL)
                        .gender(Gender.MALE)
                        .mbti("ENTJ")
                        .minAge(23)
                        .maxAge(30)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.VIETNAMESE, FoodType.CHINESE))
                        .isSchool(true)
                        .comment("함께 식사할 동료를 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile8 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member8)
                        .mateType(MateType.MEAL)
                        .gender(Gender.FEMALE)
                        .mbti("INFP")
                        .minAge(22)
                        .maxAge(29)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.CHINESE))
                        .isSchool(false)
                        .comment("맛있는 음식을 함께 즐기고 싶어요.")
                        .build());

                secondProfile7.setMember(member7);
                secondProfile8.setMember(member8);
                memberSecondProfileRepository.save(secondProfile7);
                memberSecondProfileRepository.save(secondProfile8);

                memberDataProcessor.process(secondProfile7);
                matchingDataProcessor.process(secondProfile7);
                memberDataProcessor.process(secondProfile8);
                matchingDataProcessor.process(secondProfile8);

// ===== Pair 4 =====
                MemberProfile profile9 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("운동광태민")
                        .profileImage("https://example.com/profile9.jpg")
                        .studentNumber(27)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.ESTP)
                        .hobbies(Set.of(Hobby.SPORTS, Hobby.CYCLING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("taemin@example.com")
                        .build());

                MemberProfile profile10 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("조용한소영")
                        .profileImage("https://example.com/profile10.jpg")
                        .studentNumber(28)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.INFJ)
                        .hobbies(Set.of(Hobby.READING, Hobby.WRITING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("soyoung_kakao")
                        .build());

                Member member9 = memberRepository.save(Member.builder()
                        .name("태민")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1993 - 1900, Calendar.JULY, 22))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(7788990011L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile9)
                        .build());

                Member member10 = memberRepository.save(Member.builder()
                        .name("소영")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1994 - 1900, Calendar.AUGUST, 18))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(8899001122L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile10)
                        .build());

                MemberSecondProfile secondProfile9 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member9)
                        .mateType(MateType.MEAL)
                        .gender(Gender.MALE)
                        .mbti("ESTP")
                        .minAge(24)
                        .maxAge(32)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.KOREAN))
                        .isSchool(true)
                        .comment("함께 운동할 친구를 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile10 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member10)
                        .mateType(MateType.MEAL)
                        .gender(Gender.FEMALE)
                        .mbti("INFJ")
                        .minAge(23)
                        .maxAge(30)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.KOREAN, FoodType.CHINESE))
                        .isSchool(false)
                        .comment("운동 후 함께 식사할 친구를 구해요.")
                        .build());

                secondProfile9.setMember(member9);
                secondProfile10.setMember(member10);
                memberSecondProfileRepository.save(secondProfile9);
                memberSecondProfileRepository.save(secondProfile10);

                memberDataProcessor.process(secondProfile9);
                matchingDataProcessor.process(secondProfile9);
                memberDataProcessor.process(secondProfile10);
                matchingDataProcessor.process(secondProfile10);

// ===== Pair 5 =====
                MemberProfile profile11 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("여행가민지")
                        .profileImage("https://example.com/profile11.jpg")
                        .studentNumber(29)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.ESFP)
                        .hobbies(Set.of(Hobby.TRAVELING, Hobby.PHOTOGRAPHY))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("minji_kakao")
                        .build());

                MemberProfile profile12 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("조용한태영")
                        .profileImage("https://example.com/profile12.jpg")
                        .studentNumber(30)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.ISFJ)
                        .hobbies(Set.of(Hobby.READING, Hobby.CRAFTING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("taeyoung@example.com")
                        .build());

                Member member11 = memberRepository.save(Member.builder()
                        .name("민지")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1995 - 1900, Calendar.SEPTEMBER, 5))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(9900112233L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile11)
                        .build());

                Member member12 = memberRepository.save(Member.builder()
                        .name("태영")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1996 - 1900, Calendar.OCTOBER, 12))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(1100223344L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile12)
                        .build());

                MemberSecondProfile secondProfile11 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member11)
                        .mateType(MateType.MEAL)
                        .gender(Gender.FEMALE)
                        .mbti("ESFP")
                        .minAge(23)
                        .maxAge(29)
                        .maxPeople(4)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.VIETNAMESE))
                        .isSchool(true)
                        .comment("여행 동반자를 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile12 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member12)
                        .mateType(MateType.MEAL)
                        .gender(Gender.MALE)
                        .mbti("ISFJ")
                        .minAge(22)
                        .maxAge(28)
                        .maxPeople(4)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.CHINESE))
                        .isSchool(false)
                        .comment("여행과 맛집 탐방을 좋아합니다.")
                        .build());

                secondProfile11.setMember(member11);
                secondProfile12.setMember(member12);
                memberSecondProfileRepository.save(secondProfile11);
                memberSecondProfileRepository.save(secondProfile12);

                memberDataProcessor.process(secondProfile11);
                matchingDataProcessor.process(secondProfile11);
                memberDataProcessor.process(secondProfile12);
                matchingDataProcessor.process(secondProfile12);

// ===== Pair 6 =====
                MemberProfile profile13 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("음악광승우")
                        .profileImage("https://example.com/profile13.jpg")
                        .studentNumber(31)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.ENTP)
                        .hobbies(Set.of(Hobby.PLAYING_MUSICAL_INSTRUMENTS, Hobby.SINGING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("seungwoo_kakao")
                        .build());

                MemberProfile profile14 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("문학소녀혜진")
                        .profileImage("https://example.com/profile14.jpg")
                        .studentNumber(32)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.INFP)
                        .hobbies(Set.of(Hobby.WRITING, Hobby.READING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("hyejin@example.com")
                        .build());

                Member member13 = memberRepository.save(Member.builder()
                        .name("승우")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1992 - 1900, Calendar.DECEMBER, 1))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(2211334455L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile13)
                        .build());

                Member member14 = memberRepository.save(Member.builder()
                        .name("혜진")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1993 - 1900, Calendar.MAY, 30))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(3322445566L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile14)
                        .build());

                MemberSecondProfile secondProfile13 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member13)
                        .mateType(MateType.STUDY)
                        .gender(Gender.MALE)
                        .mbti("ENTP")
                        .minAge(24)
                        .maxAge(32)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.CHINESE, FoodType.KOREAN))
                        .isSchool(true)
                        .comment("집중해서 공부할 수 있는 스터디원을 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile14 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member14)
                        .mateType(MateType.STUDY)
                        .gender(Gender.FEMALE)
                        .mbti("INFP")
                        .minAge(23)
                        .maxAge(30)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.CHINESE))
                        .isSchool(false)
                        .comment("함께 도서관에서 공부하고 싶어요.")
                        .build());

                secondProfile13.setMember(member13);
                secondProfile14.setMember(member14);
                memberSecondProfileRepository.save(secondProfile13);
                memberSecondProfileRepository.save(secondProfile14);

                memberDataProcessor.process(secondProfile13);
                matchingDataProcessor.process(secondProfile13);
                memberDataProcessor.process(secondProfile14);
                matchingDataProcessor.process(secondProfile14);

// ===== Pair 7 =====
                MemberProfile profile15 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("모험가동현")
                        .profileImage("https://example.com/profile15.jpg")
                        .studentNumber(33)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.ESTP)
                        .hobbies(Set.of(Hobby.HIKING, Hobby.CYCLING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("donghyun@example.com")
                        .build());

                MemberProfile profile16 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("예술가수빈")
                        .profileImage("https://example.com/profile16.jpg")
                        .studentNumber(34)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.INFP)
                        .hobbies(Set.of(Hobby.PAINTING, Hobby.DANCING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("subin_kakao")
                        .build());

                Member member15 = memberRepository.save(Member.builder()
                        .name("동현")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1993 - 1900, Calendar.JUNE, 12))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(4433221100L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile15)
                        .build());

                Member member16 = memberRepository.save(Member.builder()
                        .name("수빈")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1994 - 1900, Calendar.JULY, 8))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(5544332211L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile16)
                        .build());

                MemberSecondProfile secondProfile15 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member15)
                        .mateType(MateType.EXERCISE)
                        .gender(Gender.MALE)
                        .mbti("ESTP")
                        .minAge(25)
                        .maxAge(33)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.KOREAN))
                        .isSchool(true)
                        .comment("운동을 좋아하는 친구를 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile16 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member16)
                        .mateType(MateType.EXERCISE)
                        .gender(Gender.FEMALE)
                        .mbti("INFP")
                        .minAge(24)
                        .maxAge(31)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.CHINESE))
                        .isSchool(false)
                        .comment("함께 달리기를 즐길 친구를 찾습니다.")
                        .build());

                secondProfile15.setMember(member15);
                secondProfile16.setMember(member16);
                memberSecondProfileRepository.save(secondProfile15);
                memberSecondProfileRepository.save(secondProfile16);

                memberDataProcessor.process(secondProfile15);
                matchingDataProcessor.process(secondProfile15);
                memberDataProcessor.process(secondProfile16);
                matchingDataProcessor.process(secondProfile16);

// ===== Pair 8 =====
                MemberProfile profile17 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("책벌레은정")
                        .profileImage("https://example.com/profile17.jpg")
                        .studentNumber(35)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.INFJ)
                        .hobbies(Set.of(Hobby.READING, Hobby.WRITING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("eunjung_kakao")
                        .build());

                MemberProfile profile18 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("스포츠마스터민호")
                        .profileImage("https://example.com/profile18.jpg")
                        .studentNumber(36)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.ESTP)
                        .hobbies(Set.of(Hobby.SPORTS, Hobby.CYCLING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("minho@example.com")
                        .build());

                Member member17 = memberRepository.save(Member.builder()
                        .name("은정")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1994 - 1900, Calendar.MAY, 5))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(6655443322L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile17)
                        .build());

                Member member18 = memberRepository.save(Member.builder()
                        .name("민호")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1993 - 1900, Calendar.AUGUST, 30))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(7766554433L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile18)
                        .build());

                MemberSecondProfile secondProfile17 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member17)
                        .mateType(MateType.MEAL)
                        .gender(Gender.FEMALE)
                        .mbti("INFJ")
                        .minAge(23)
                        .maxAge(30)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.VIETNAMESE))
                        .isSchool(true)
                        .comment("함께 식사할 친구를 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile18 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member18)
                        .mateType(MateType.MEAL)
                        .gender(Gender.MALE)
                        .mbti("ESTP")
                        .minAge(22)
                        .maxAge(29)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.KOREAN))
                        .isSchool(false)
                        .comment("맛있는 음식과 함께 할 동료를 구합니다.")
                        .build());

                secondProfile17.setMember(member17);
                secondProfile18.setMember(member18);
                memberSecondProfileRepository.save(secondProfile17);
                memberSecondProfileRepository.save(secondProfile18);

                memberDataProcessor.process(secondProfile17);
                matchingDataProcessor.process(secondProfile17);
                memberDataProcessor.process(secondProfile18);
                matchingDataProcessor.process(secondProfile18);

// ===== Pair 9 =====
                MemberProfile profile19 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("요리천재지훈")
                        .profileImage("https://example.com/profile19.jpg")
                        .studentNumber(37)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.ENTJ)
                        .hobbies(Set.of(Hobby.COOKING, Hobby.BAKING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("jihoon@example.com")
                        .build());

                MemberProfile profile20 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("예술혼수민")
                        .profileImage("https://example.com/profile20.jpg")
                        .studentNumber(38)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.INFP)
                        .hobbies(Set.of(Hobby.PAINTING, Hobby.SINGING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("min_kakao")
                        .build());

                Member member19 = memberRepository.save(Member.builder()
                        .name("지훈")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1994 - 1900, Calendar.OCTOBER, 3))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(8877665544L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile19)
                        .build());

                Member member20 = memberRepository.save(Member.builder()
                        .name("민")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1995 - 1900, Calendar.NOVEMBER, 11))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(9988776655L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile20)
                        .build());

                MemberSecondProfile secondProfile19 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member19)
                        .mateType(MateType.MEAL)
                        .gender(Gender.MALE)
                        .mbti("ENTJ")
                        .minAge(24)
                        .maxAge(31)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.CHINESE, FoodType.VIETNAMESE))
                        .isSchool(true)
                        .comment("함께 식사할 동료를 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile20 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member20)
                        .mateType(MateType.MEAL)
                        .gender(Gender.FEMALE)
                        .mbti("INFP")
                        .minAge(23)
                        .maxAge(30)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.KOREAN))
                        .isSchool(false)
                        .comment("맛있는 식사를 함께 할 친구를 찾습니다.")
                        .build());

                secondProfile19.setMember(member19);
                secondProfile20.setMember(member20);
                memberSecondProfileRepository.save(secondProfile19);
                memberSecondProfileRepository.save(secondProfile20);

                memberDataProcessor.process(secondProfile19);
                matchingDataProcessor.process(secondProfile19);
                memberDataProcessor.process(secondProfile20);
                matchingDataProcessor.process(secondProfile20);

// ===== Pair 10 =====
                MemberProfile profile21 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("활동가수진2")
                        .profileImage("https://example.com/profile21.jpg")
                        .studentNumber(39)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.ENTP)
                        .hobbies(Set.of(Hobby.TRAVELING, Hobby.CYCLING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("sujin2@example.com")
                        .build());

                MemberProfile profile22 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("조용한민준")
                        .profileImage("https://example.com/profile22.jpg")
                        .studentNumber(40)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.INFJ)
                        .hobbies(Set.of(Hobby.READING, Hobby.WRITING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("minjun_kakao")
                        .build());

                Member member21 = memberRepository.save(Member.builder()
                        .name("수진2")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1994 - 1900, Calendar.DECEMBER, 25))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(1122446688L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile21)
                        .build());

                Member member22 = memberRepository.save(Member.builder()
                        .name("민준")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1995 - 1900, Calendar.JANUARY, 17))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(2233557799L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile22)
                        .build());

                MemberSecondProfile secondProfile21 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member21)
                        .mateType(MateType.ALL)
                        .gender(Gender.FEMALE)
                        .mbti("ENTP")
                        .minAge(25)
                        .maxAge(32)
                        .maxPeople(4)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.CHINESE))
                        .isSchool(true)
                        .comment("모든 친구와 함께 할 준비가 되어있습니다.")
                        .build());

                MemberSecondProfile secondProfile22 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member22)
                        .mateType(MateType.ALL)
                        .gender(Gender.MALE)
                        .mbti("INFJ")
                        .minAge(24)
                        .maxAge(31)
                        .maxPeople(4)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.KOREAN))
                        .isSchool(false)
                        .comment("다양한 활동에 참여하고 싶습니다.")
                        .build());

                secondProfile21.setMember(member21);
                secondProfile22.setMember(member22);
                memberSecondProfileRepository.save(secondProfile21);
                memberSecondProfileRepository.save(secondProfile22);

                memberDataProcessor.process(secondProfile21);
                matchingDataProcessor.process(secondProfile21);
                memberDataProcessor.process(secondProfile22);
                matchingDataProcessor.process(secondProfile22);

// ===== Pair 11 =====
                MemberProfile profile23 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("책벌레태희")
                        .profileImage("https://example.com/profile23.jpg")
                        .studentNumber(41)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.INFJ)
                        .hobbies(Set.of(Hobby.READING, Hobby.WRITING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("taehee_kakao")
                        .build());

                MemberProfile profile24 = memberProfileRepository.save(MemberProfile.builder()
                        .nickname("스포츠킹민혁")
                        .profileImage("https://example.com/profile24.jpg")
                        .studentNumber(42)
                        .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                        .MBTI(MBTI.ESTP)
                        .hobbies(Set.of(Hobby.SPORTS, Hobby.GARDENING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("minhyuk@example.com")
                        .build());

// 생일과 socialId는 기존과 중복되지 않도록 변경
                Member member23 = memberRepository.save(Member.builder()
                        .name("태희")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(1995 - 1900, Calendar.MARCH, 8))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(3344666677L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile23)
                        .build());

                Member member24 = memberRepository.save(Member.builder()
                        .name("민혁")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1996 - 1900, Calendar.JULY, 19))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(4455777788L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile24)
                        .build());

                MemberSecondProfile secondProfile23 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member23)
                        .mateType(MateType.MEAL)
                        .gender(Gender.FEMALE)
                        .mbti("INFJ")
                        .minAge(20)
                        .maxAge(26)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.CHINESE, FoodType.VIETNAMESE))
                        .isSchool(true)
                        .comment("조용하게 공부할 동료를 찾습니다.")
                        .build());

                MemberSecondProfile secondProfile24 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
                        .member(member24)
                        .mateType(MateType.MEAL)
                        .gender(Gender.MALE)
                        .mbti("INFJ")
                        .minAge(21)
                        .maxAge(28)
                        .maxPeople(3)
                        .exerciseType(ExerciseType.RUNNING)
                        .foodTypes(Set.of(FoodType.KOREAN))
                        .isSchool(false)
                        .comment("함께 운동할 친구를 구해요.")
                        .build());

                secondProfile23.setMember(member23);
                secondProfile24.setMember(member24);
                memberSecondProfileRepository.save(secondProfile23);
                memberSecondProfileRepository.save(secondProfile24);

                memberDataProcessor.process(secondProfile23);
                matchingDataProcessor.process(secondProfile23);
                memberDataProcessor.process(secondProfile24);
                matchingDataProcessor.process(secondProfile24);

            }

        }

    }
}

