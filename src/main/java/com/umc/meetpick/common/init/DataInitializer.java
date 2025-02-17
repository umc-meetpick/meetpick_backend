package com.umc.meetpick.common.init;

import com.umc.meetpick.entity.*;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.entity.mapping.MemberSecondProfileSubMajor;
import com.umc.meetpick.entity.mapping.MemberSecondProfileTimes;
import com.umc.meetpick.enums.*;
import com.umc.meetpick.repository.*;
import com.umc.meetpick.repository.member.*;
import com.umc.meetpick.repository.member.MemberProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

                MemberProfile profile1= MemberProfile.builder()
                        .nickname("베티")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/graduate.png")
                        .studentNumber(20)
                        .subMajor(subMajorRepository.findByNameOrderByName("커뮤니케이션학과"))
                        .MBTI(MBTI.ISFP)
                        .hobbies(Set.of(Hobby.GAMING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("example8")
                        .build();

                MemberProfile profile2 = MemberProfile.builder()
                        .nickname("디아")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/default.png")
                        .studentNumber(20)
                        .subMajor(subMajorRepository.findByNameOrderByName("자유전공학부"))
                        .MBTI(MBTI.ISTP)
                        .hobbies(Set.of(Hobby.TRAVELING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("example1")
                        .build();

                MemberProfile profile3 = MemberProfile.builder()
                        .nickname("지니")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/boxing.png")
                        .studentNumber(22)
                        .subMajor(subMajorRepository.findByNameOrderByName("정보보안학과"))
                        .MBTI(MBTI.INFP)
                        .hobbies(Set.of(Hobby.BADMINTON))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("example2")
                        .build();

                MemberProfile profile4 = MemberProfile.builder()
                        .nickname("한글")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/magician.png")
                        .studentNumber(21)
                        .subMajor(subMajorRepository.findByNameOrderByName("정보보안학과"))
                        .MBTI(MBTI.INFJ)
                        .hobbies(Set.of(Hobby.FITNESS))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("example3")
                        .build();

                MemberProfile profile5 = MemberProfile.builder()
                        .nickname("주니")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/earphone.png")
                        .studentNumber(20)
                        .subMajor(subMajorRepository.findByNameOrderByName("정보보안학과"))
                        .MBTI(MBTI.ENTP)
                        .hobbies(Set.of(Hobby.BADMINTON))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("example4")
                        .build();

                MemberProfile profile6 = MemberProfile.builder()
                        .nickname("코리")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/hoody.png")
                        .studentNumber(20)
                        .subMajor(subMajorRepository.findByNameOrderByName("정보보안학과"))
                        .MBTI(MBTI.INFP)
                        .hobbies(Set.of(Hobby.GAMING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("example5")
                        .build();

                MemberProfile profile7 = MemberProfile.builder()
                        .nickname("탱")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/muffler.png")
                        .studentNumber(22)
                        .subMajor(subMajorRepository.findByNameOrderByName("정보보안학과"))
                        .MBTI(MBTI.ESTJ)
                        .hobbies(Set.of(Hobby.LISTENING_TO_MUSIC))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("example6")
                        .build();

                MemberProfile profile8 = MemberProfile.builder()
                        .nickname("이서")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/study.png")
                        .studentNumber(23)
                        .subMajor(subMajorRepository.findByNameOrderByName("정보보안학과"))
                        .MBTI(MBTI.INFJ)
                        .hobbies(Set.of(Hobby.COOKING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("example7")
                        .build();

                MemberProfile profile9 = MemberProfile.builder()
                        .nickname("마리")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/hamburger.png")
                        .studentNumber(22)
                        .subMajor(subMajorRepository.findByNameOrderByName("정보보안학과"))
                        .MBTI(MBTI.INFP)
                        .hobbies(Set.of(Hobby.GAMING))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("example8")
                        .build();

                // 2. Member 생성 (MemberProfile 포함)
                Member member1 = Member.builder()
                        .name("윤빈")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(2000-12-31))
                        .university(University.SOONGSHIL_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(1L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile1)
                        .isVerified(true)
                        .build();

                Member member2 = Member.builder()
                        .name("나리")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(2003-12-31))
                        .university(University.SOONGSHIL_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(2L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile2)
                        .isVerified(true)
                        .build();

                Member member3 = Member.builder()
                        .name("태진")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(1998-12-31))
                        .university(University.CHUNGANG_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(3L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile3)
                        .isVerified(true)
                        .build();

                Member member4 = Member.builder()
                        .name("준형")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(2002-4-15))
                        .university(University.CHUNGANG_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(4L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile4)
                        .isVerified(true)
                        .build();

                Member member5 = Member.builder()
                        .name("지훈")
                        .gender(Gender.MALE)
                        .birthday(new java.util.Date(2001-12-31))
                        .university(University.SOONGSHIL_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(5L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile5)
                        .isVerified(true)
                        .build();

                Member member6 = Member.builder()
                        .name("지연")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(2001-12-31))
                        .university(University.SUNGSHIN_WOMANS_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(6L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile6)
                        .isVerified(true)
                        .build();

                Member member7 = Member.builder()
                        .name("태영")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(2003-12-31))
                        .university(University.SUNGSHIN_WOMANS_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(7L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile7)
                        .isVerified(true)
                        .build();

                Member member8 = Member.builder()
                        .name("서윤")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(2004-12-31))
                        .university(University.SEOUL_WOMANS_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(8L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile8)
                        .isVerified(true)
                        .build();

                Member member9 = Member.builder()
                        .name("하리")
                        .gender(Gender.FEMALE)
                        .birthday(new java.util.Date(2003-12-31))
                        .university(University.SUNGSHIN_WOMANS_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(9L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile9)
                        .isVerified(true)
                        .build();


                memberProfileRepository.save(profile1);
                memberProfileRepository.save(profile2);
                memberProfileRepository.save(profile3);
                memberProfileRepository.save(profile4);
                memberProfileRepository.save(profile5);
                memberProfileRepository.save(profile6);
                memberProfileRepository.save(profile7);
                memberProfileRepository.save(profile8);
                memberProfileRepository.save(profile9);

                memberRepository.save(member1);
                memberRepository.save(member2);
                memberRepository.save(member3);
                memberRepository.save(member4);
                memberRepository.save(member5);
                memberRepository.save(member6);
                memberRepository.save(member7);
                memberRepository.save(member8);
                memberRepository.save(member9);

                // Profile 1
                MemberSecondProfile secondProfile11 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member1)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("ENTJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(0)
                                .comment("조용하게 공부할 동료를 찾습니다.")
                                .exerciseType(ExerciseType.RUNNING)
                                .isSchool(true)
                                .foodTypes(Set.of(FoodType.CHINESE, FoodType.VIETNAMESE))
                                .studyType(StudyType.STUDY)
                                .majorName("컴퓨터공학")
                                .professorName("김교수님")
                                .isOnline(false)
                                .studyTimes(1)
                                .place("카페")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile12 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member1)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("ENTJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(0)
                                .comment("맛있는거 먹자.")
                                .exerciseType(ExerciseType.RUNNING)
                                .isSchool(true)
                                .foodTypes(Set.of(FoodType.CHINESE, FoodType.VIETNAMESE))
                                .studyType(StudyType.STUDY)
                                .majorName("컴퓨터공학")
                                .professorName("김교수님")
                                .isOnline(false)
                                .studyTimes(1)
                                .place("카페")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile13 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member1)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("ENTJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(0)
                                .comment("열심히 운동하자")
                                .exerciseType(ExerciseType.RUNNING)
                                .isSchool(true)
                                .foodTypes(Set.of(FoodType.CHINESE, FoodType.VIETNAMESE))
                                .studyType(StudyType.STUDY)
                                .majorName("컴퓨터공학")
                                .professorName("김교수님")
                                .isOnline(false)
                                .studyTimes(1)
                                .place("카페")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

// Profile 2
                MemberSecondProfile secondProfile2 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member2)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(19)
                                .maxAge(25)
                                .mbti("INFP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(1)
                                .comment("함께 과제를 할 동료를 찾습니다.")
                                .exerciseType(ExerciseType.FITNESS)
                                .isSchool(false)
                                .foodTypes(Set.of(FoodType.KOREAN, FoodType.WESTERN))
                                .studyType(StudyType.MAJOR)
                                .majorName("경영학")
                                .professorName("이교수님")
                                .isOnline(true)
                                .studyTimes(2)
                                .place("도서관")
                                .mateType(MateType.STUDY)
                                .build()
                );

// Profile 3
                MemberSecondProfile secondProfile3 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member3)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.JUNIOR)
                                .minAge(21)
                                .maxAge(27)
                                .mbti("ESTP")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(5)
                                .currentPeople(0)
                                .comment("함께 달리기를 할 파트너를 찾습니다.")
                                .exerciseType(ExerciseType.BASKETBALL)
                                .isSchool(true)
                                .foodTypes(Set.of(FoodType.JAPANESE))
                                .studyType(StudyType.MAJOR)
                                .majorName("체육학")
                                .professorName("박교수님")
                                .isOnline(false)
                                .studyTimes(1)
                                .place("운동장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

// Profile 4
                MemberSecondProfile secondProfile4 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member4)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(22)
                                .maxAge(28)
                                .mbti("ISFJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(0)
                                .comment("맛있는 음식을 즐길 동료를 찾습니다.")
                                .exerciseType(ExerciseType.BOWLING)
                                .isSchool(true)
                                .foodTypes(Set.of(FoodType.CHINESE, FoodType.KOREAN))
                                .studyType(StudyType.NON_MAJOR)
                                .majorName("음식문화")
                                .professorName("최교수님")
                                .isOnline(false)
                                .studyTimes(2)
                                .place("레스토랑")
                                .mateType(MateType.MEAL)
                                .build()
                );

// Profile 5
                MemberSecondProfile secondProfile5 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member5)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.JUNIOR)
                                .minAge(18)
                                .maxAge(24)
                                .mbti("INTJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(0)
                                .comment("조용한 도서관에서 공부할 동료를 찾습니다.")
                                .exerciseType(null) // 운동 관련 필드 미사용
                                .isSchool(false)
                                .foodTypes(Set.of(FoodType.KOREAN))
                                .studyType(StudyType.MAJOR)
                                .majorName("수학")
                                .professorName("정교수님")
                                .isOnline(true)
                                .studyTimes(3)
                                .place("도서관")
                                .mateType(MateType.STUDY)
                                .build()
                );

// Profile 6
                MemberSecondProfile secondProfile6 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member6)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("ENFP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("헬스장에서 같이 운동할 파트너를 찾습니다.")
                                .exerciseType(ExerciseType.FITNESS)
                                .isSchool(true)
                                .foodTypes(Set.of(FoodType.CHINESE))
                                .studyType(StudyType.MAJOR)
                                .majorName("스포츠과학")
                                .professorName("오교수님")
                                .isOnline(false)
                                .studyTimes(1)
                                .place("헬스장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

// Profile 7
                MemberSecondProfile secondProfile7 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member7)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.JUNIOR)
                                .minAge(21)
                                .maxAge(27)
                                .mbti("ISTP")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(5)
                                .currentPeople(2)
                                .comment("맛집 탐방 동료를 찾습니다.")
                                .exerciseType(null)
                                .isSchool(false)
                                .foodTypes(Set.of(FoodType.KOREAN, FoodType.JAPANESE))
                                .studyType(StudyType.MAJOR)
                                .majorName("요리학")
                                .professorName("홍교수님")
                                .isOnline(true)
                                .studyTimes(2)
                                .place("시내")
                                .mateType(MateType.MEAL)
                                .build()
                );

// Profile 8
                MemberSecondProfile secondProfile8 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member8)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(22)
                                .maxAge(28)
                                .mbti("ESFJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(0)
                                .comment("온라인 스터디에 함께할 동료를 찾습니다.")
                                .exerciseType(null)
                                .isSchool(true)
                                .foodTypes(Set.of(FoodType.JAPANESE))
                                .studyType(StudyType.MAJOR)
                                .majorName("문학")
                                .professorName("김교수님")
                                .isOnline(true)
                                .studyTimes(3)
                                .place("스터디룸")
                                .mateType(MateType.STUDY)
                                .build()
                );

// Profile 9
                MemberSecondProfile secondProfile9 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member9)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.JUNIOR)
                                .minAge(18)
                                .maxAge(24)
                                .mbti("INFJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(0)
                                .comment("요가 수업에 같이 참여할 파트너를 찾습니다.")
                                .exerciseType(ExerciseType.RUNNING)
                                .isSchool(false)
                                .foodTypes(Set.of(FoodType.CHINESE, FoodType.KOREAN))
                                .studyType(StudyType.STUDY)
                                .majorName("심리학")
                                .professorName("이교수님")
                                .isOnline(false)
                                .studyTimes(1)
                                .place("요가 스튜디오")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                memberSecondProfileRepository.save(secondProfile11);
                memberSecondProfileRepository.save(secondProfile12);
                memberSecondProfileRepository.save(secondProfile13);
                memberSecondProfileRepository.save(secondProfile2);
                memberSecondProfileRepository.save(secondProfile3);
                memberSecondProfileRepository.save(secondProfile4);
                memberSecondProfileRepository.save(secondProfile5);
                memberSecondProfileRepository.save(secondProfile6);
                memberSecondProfileRepository.save(secondProfile7);
                memberSecondProfileRepository.save(secondProfile8);
                memberSecondProfileRepository.save(secondProfile9);

                memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member2)
                        .memberSecondProfile(secondProfile11)
                        .status(false)
                        .isAccepted(false)
                        .build());

                memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member3)
                        .memberSecondProfile(secondProfile13)
                        .status(false)
                        .isAccepted(false)
                        .build());

                memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member4)
                        .memberSecondProfile(secondProfile12)
                        .status(true)
                        .isAccepted(true)
                        .build());

                memberMappingRepository.save(MemberSecondProfileMapping.builder()
                        .member(member5)
                        .memberSecondProfile(secondProfile11)
                        .status(true)
                        .isAccepted(true)
                        .build());

            }
        }
    }
}

