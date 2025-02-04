package com.umc.meetpick.common.init;

import com.umc.meetpick.entity.*;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.*;
import com.umc.meetpick.repository.*;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.member.*;
import com.umc.meetpick.repository.member.MemberProfileRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Set;

@Component
@Transactional
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final MajorRepository majorRepository;
    private final SubMajorRepository subMajorRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final MemberRepository memberRepository;
    private final MemberSecondProfileRepository memberSecondProfileRepository;
    private final PersonalityRepository personalityRepository;
    private final MemberMappingRepository memberMappingRepository;

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
//        if (memberRepository.count() == 0) {
//            Major major1 = majorRepository.save(new Major("컴퓨터공학과"));
//            Major major2 = majorRepository.save(new Major("전자공학과"));
//
//            // 1️⃣ Member 먼저 저장
//            Member member1 = memberRepository.save(Member.builder()
//                    .name("개똥이")
//                    .gender(Gender.MALE)
//                    .birthday(new java.util.Date(1995 - 1900, Calendar.JUNE, 15))
//                    .university(University.SEOUL_NATIONAL_UNIVERSITY)
//                    .socialType(SocialType.KAKAO)
//                    .socialId(1234567890L)
//                    .status(MemberStatus.ACTIVE)
//                    .role(MemberRole.MEMBER)
//                    .build());
//
//            Member member2 = memberRepository.save(Member.builder()
//                    .name("말똥이")
//                    .gender(Gender.FEMALE)
//                    .birthday(new java.util.Date(1998 - 1900, Calendar.APRIL, 20))
//                    .university(University.CHUNGANG_UNIVERSITY)
//                    .socialType(SocialType.KAKAO)
//                    .socialId(9876543210L)
//                    .status(MemberStatus.ACTIVE)
//                    .role(MemberRole.MEMBER)
//                    .build());
//
//            // 2️⃣ MemberProfile 생성 후 Member와 연결
//            MemberProfile profile1 = MemberProfile.builder()
//                    .nickname("코딩마스터")
//                    .profileImage("https://example.com/profile1.jpg")
//                    .studentNumber(20200001)
//                    .MBTI(MBTI.INTP)
//                    .major(major1)
//                    .hobbies(Set.of(Hobby.READING, Hobby.COLLECTING))
//                    .contact(ContactType.KAKAO_TALK_ID)
//                    .contactInfo("kakao_id_1")
//                    .member(member1) // Member와 연결
//                    .build();
//
//            MemberProfile profile2 = MemberProfile.builder()
//                    .nickname("전자왕")
//                    .profileImage("https://example.com/profile2.jpg")
//                    .studentNumber(20190015)
//                    .MBTI(MBTI.ENTJ)
//                    .major(major2)
//                    .hobbies(Set.of(Hobby.GARDENING, Hobby.KNITTING))
//                    .contact(ContactType.PHONE_NUMBER)
//                    .contactInfo("electro_master@example.com")
//                    .member(member2) // Member와 연결
//                    .build();
//
////            profile1 = memberProfileRepository.save(profile1);
////            profile2 = memberProfileRepository.save(profile2);
////
////            // 3️⃣ Member에 MemberProfile 연결
////            member1.setMemberProfile(profile1);
////            member2.setMemberProfile(profile2);
////
////            memberRepository.save(member1);
////            memberRepository.save(member2);
//            // ✅ MemberProfile과 Member 연결 (양방향 설정)
//            profile1.setMember(member1);
//            profile2.setMember(member2);
//            member1.setMemberProfile(profile1);
//            member2.setMemberProfile(profile2);
//
//            // 3️⃣ Member 먼저 저장 (Cascade 적용됨)
//            member1 = memberRepository.save(member1);
//            member2 = memberRepository.save(member2);
//
//            // 4️⃣ MemberProfile도 저장 (Cascade 자동 적용)
//            memberProfileRepository.save(profile1);
//            memberProfileRepository.save(profile2);
//
//            // 4️⃣ MemberSecondProfile 생성 및 저장
//            MemberSecondProfile secondProfile1 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
//                    .member(member1)
//                    .mateType(MateType.MEAL)
//                    .minAge(20)
//                    .maxAge(30)
//                    .personality(personalityRepository.save(new Personality(PersonalityEnum.CHEERFUL, PersonalityEnum.OBJECTIVE, PersonalityEnum.SUBJECTIVE, PersonalityEnum.QUIET)))
//                    .exerciseTypes(Set.of(ExerciseType.BOWLING, ExerciseType.RUNNING))
//                    .foodTypes(Set.of(FoodType.KOREAN, FoodType.JAPANESE))
//                    .isSchool(true)
//                    .comment("운동 같이 할 사람 구함!")
//                    .build());
//
//            MemberSecondProfile secondProfile2 = memberSecondProfileRepository.save(MemberSecondProfile.builder()
//                    .member(member2)
//                    .mateType(MateType.STUDY)
//                    .minAge(22)
//                    .maxAge(28)
//                    .personality(personalityRepository.save(new Personality(PersonalityEnum.CHEERFUL, PersonalityEnum.OBJECTIVE, PersonalityEnum.SUBJECTIVE, PersonalityEnum.QUIET)))
//                    .exerciseTypes(Set.of(ExerciseType.BOWLING))
//                    .foodTypes(Set.of(FoodType.WESTERN, FoodType.CHINESE))
//                    .isSchool(false)
//                    .comment("조용한 스터디 모임 찾습니다.")
//                    .build());
//
//            // 5️⃣ Member에 MemberSecondProfile 연결
//            member1.setMemberSecondProfile(secondProfile1);
//            member2.setMemberSecondProfile(secondProfile2);
//
//            memberRepository.save(member1);
//            memberRepository.save(member2);
//        }

        // 6️⃣ SubMajor 데이터 저장 (기존 코드 유지)
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
    }
}
