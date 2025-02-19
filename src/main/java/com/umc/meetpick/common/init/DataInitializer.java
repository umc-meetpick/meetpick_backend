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

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final MajorRepository majorRepository;
    private final SubMajorRepository subMajorRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final MemberRepository memberRepository;
    private final MemberSecondProfileRepository memberSecondProfileRepository;
    private final MemberMappingRepository memberMappingRepository;
    private final MemberSecondProfileTimesRepository memberSecondProfileTimesRepository;

    /*@PostConstruct
    public void init() {
        // FULLTEXT INDEX 생성 쿼리 실행
        String createIndexQuery = "CREATE FULLTEXT INDEX university_index ON university(universityName) WITH PARSER ngram(1)";
        jdbcTemplate.execute(createIndexQuery);
    } mysql 사용 시 사용하기 */

    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
                        .contactInfo("example9")
                        .build();

                MemberProfile profile10 = MemberProfile.builder()
                        .nickname("지원1")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile10.png")
                        .studentNumber(23)
                        .subMajor(subMajorRepository.findByNameOrderByName("인공지능학과"))
                        .MBTI(MBTI.INFP)
                        .hobbies(Set.of(Hobby.GAMING, Hobby.DEVELOPMENT))
                        .contact(ContactType.OPEN_CHAT_LINK)
                        .contactInfo("jiwon_chat")
                        .build();

                MemberProfile profile11 = MemberProfile.builder()
                        .nickname("서연")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile11.png")
                        .studentNumber(24)
                        .subMajor(subMajorRepository.findByNameOrderByName("게임학과"))
                        .MBTI(MBTI.ENTJ)
                        .hobbies(Set.of(Hobby.WRITING, Hobby.READING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("seoyeon_kakao")
                        .build();

                MemberProfile profile12 = MemberProfile.builder()
                        .nickname("지수1")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile12.png")
                        .studentNumber(21)
                        .subMajor(subMajorRepository.findByNameOrderByName("게임학과"))
                        .MBTI(MBTI.INTP)
                        .hobbies(Set.of(Hobby.GAMING, Hobby.COOKING))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("jisu_kakao")
                        .build();

                MemberProfile profile13 = MemberProfile.builder()
                        .nickname("민수")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile13.png")
                        .studentNumber(22)
                        .subMajor(subMajorRepository.findByNameOrderByName("기계공학과"))
                        .MBTI(MBTI.ISTJ)
                        .hobbies(Set.of(Hobby.READING, Hobby.PUZZLE_SOLVING))
                        .contact(ContactType.OPEN_CHAT_LINK)
                        .contactInfo("minsu_chat")
                        .build();

                MemberProfile profile14 = MemberProfile.builder()
                        .nickname("도현1")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile14.png")
                        .studentNumber(25)
                        .subMajor(subMajorRepository.findByNameOrderByName("건축공학과"))
                        .MBTI(MBTI.ENFP)
                        .hobbies(Set.of(Hobby.HIKING, Hobby.BADMINTON))
                        .contact(ContactType.OPEN_CHAT_LINK)
                        .contactInfo("dohyun_chat")
                        .build();

                MemberProfile profile15 = MemberProfile.builder()
                        .nickname("윤아")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile15.png")
                        .studentNumber(23)
                        .subMajor(subMajorRepository.findByNameOrderByName("전기전자공학과"))
                        .MBTI(MBTI.ISTP)
                        .hobbies(Set.of(Hobby.CYCLING, Hobby.DEVELOPMENT))
                        .contact(ContactType.PHONE_NUMBER)
                        .contactInfo("yoonah_phone")
                        .build();

                MemberProfile profile16 = MemberProfile.builder()
                        .nickname("지훈1")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile16.png")
                        .studentNumber(24)
                        .subMajor(subMajorRepository.findByNameOrderByName("심리학과"))
                        .MBTI(MBTI.INFP)
                        .hobbies(Set.of(Hobby.TRAVELING, Hobby.YOGA))
                        .contact(ContactType.KAKAO_TALK_ID)
                        .contactInfo("jihoon_kakao")
                        .build();

                MemberProfile profile17 = MemberProfile.builder()
                        .nickname("수진1")
                        .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile17.png")
                        .studentNumber(26)
                        .subMajor(subMajorRepository.findByNameOrderByName("의학과"))
                        .MBTI(MBTI.ENTP)
                        .hobbies(Set.of(Hobby.LISTENING_TO_MUSIC, Hobby.SWIMMING))
                        .contact(ContactType.OPEN_CHAT_LINK)
                        .contactInfo("sujin_chat")
                        .build();
                MemberProfile profile18 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("도경")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile18.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("화학공학과"))
                                .MBTI(MBTI.ESFJ)
                                .hobbies(Set.of(Hobby.COOKING, Hobby.YOGA))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("dokyung_kakao")
                                .build()
                );

                MemberProfile profile19 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("은지1")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile19.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("간호학과"))
                                .MBTI(MBTI.ISFJ)
                                .hobbies(Set.of(Hobby.READING, Hobby.GARDENING))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("eunji_phone")
                                .build()
                );

                MemberProfile profile20 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("하영")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile20.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("환경공학과"))
                                .MBTI(MBTI.INTJ)
                                .hobbies(Set.of(Hobby.HIKING, Hobby.CAMPING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("hayoung_chat")
                                .build()
                );

                MemberProfile profile21 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("석민")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile21.png")
                                .studentNumber(25)
                                .subMajor(subMajorRepository.findByNameOrderByName("건설방재공학과"))
                                .MBTI(MBTI.ENTP)
                                .hobbies(Set.of(Hobby.TENNIS, Hobby.FITNESS))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("seokmin_kakao")
                                .build()
                );

                MemberProfile profile22 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("혜린")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile22.png")
                                .studentNumber(21)
                                .subMajor(subMajorRepository.findByNameOrderByName("국어국문학과"))
                                .MBTI(MBTI.ISFP)
                                .hobbies(Set.of(Hobby.WRITING, Hobby.LISTENING_TO_MUSIC))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("hyerin_phone")
                                .build()
                );

                MemberProfile profile23 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("진우2")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile23.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("수학과"))
                                .MBTI(MBTI.ISTP)
                                .hobbies(Set.of(Hobby.PUZZLE_SOLVING, Hobby.READING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("jinwoo_chat")
                                .build()
                );

                MemberProfile profile24 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("다혜")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile24.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("경제학과"))
                                .MBTI(MBTI.ESFP)
                                .hobbies(Set.of(Hobby.TRAVELING, Hobby.SOCCER))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("dahye_kakao")
                                .build()
                );

                MemberProfile profile25 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("민혁")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile25.png")
                                .studentNumber(25)
                                .subMajor(subMajorRepository.findByNameOrderByName("특수교육과"))
                                .MBTI(MBTI.ESTP)
                                .hobbies(Set.of(Hobby.BADMINTON, Hobby.TENNIS))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("minhyuk_chat")
                                .build()
                );

                MemberProfile profile26 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("서진")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile26.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                                .MBTI(MBTI.ENFJ)
                                .hobbies(Set.of(Hobby.PAINTING, Hobby.VIOLIN))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("seojin_kakao")
                                .build()
                );

                MemberProfile profile27 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("지훈12")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile27.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("농업경제학과"))
                                .MBTI(MBTI.ENTJ)
                                .hobbies(Set.of(Hobby.CYCLING, Hobby.YOGA))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("jihoon_phone")
                                .build()
                );

                MemberProfile profile28 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("채영")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile28.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("약학과"))
                                .MBTI(MBTI.INFJ)
                                .hobbies(Set.of(Hobby.HANDMADE, Hobby.COOKING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("chaeyoung_chat")
                                .build()
                );
                MemberProfile profile29 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("정민")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile29.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                                .MBTI(MBTI.ENFP)
                                .hobbies(Set.of(Hobby.TRAVELING, Hobby.LISTENING_TO_MUSIC))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("jungmin_chat")
                                .build()
                );

                MemberProfile profile30 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("예린1")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile30.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("정보보안학과"))
                                .MBTI(MBTI.INTP)
                                .hobbies(Set.of(Hobby.GAMING, Hobby.DEVELOPMENT))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("yerin_kakao")
                                .build()
                );
                MemberProfile profile31 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("준영")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile31.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("기계공학과"))
                                .MBTI(MBTI.ESTP)
                                .hobbies(Set.of(Hobby.FITNESS, Hobby.BADMINTON))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("joonyoung_phone")
                                .build()
                );

                MemberProfile profile32 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("지수")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile32.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("전기전자공학과"))
                                .MBTI(MBTI.ENTJ)
                                .hobbies(Set.of(Hobby.TENNIS, Hobby.YOGA))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("jisu_kakao")
                                .build()
                );

                MemberProfile profile33 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("윤호")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile33.png")
                                .studentNumber(21)
                                .subMajor(subMajorRepository.findByNameOrderByName("건축공학과"))
                                .MBTI(MBTI.ISTP)
                                .hobbies(Set.of(Hobby.HIKING, Hobby.TRAVELING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("yoonho_chat")
                                .build()
                );

                MemberProfile profile34 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("채린")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile34.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("간호학과"))
                                .MBTI(MBTI.INFP)
                                .hobbies(Set.of(Hobby.SWIMMING, Hobby.YOGA))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("chaerin_phone")
                                .build()
                );

                MemberProfile profile35 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("형준")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile35.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("정보통신공학과"))
                                .MBTI(MBTI.ENTP)
                                .hobbies(Set.of(Hobby.CYCLING, Hobby.FITNESS))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("hyungjun_kakao")
                                .build()
                );

                MemberProfile profile36 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("다영")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile36.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                                .MBTI(MBTI.ISFJ)
                                .hobbies(Set.of(Hobby.BADMINTON, Hobby.TRAVELING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("dayoung_chat")
                                .build()
                );

                MemberProfile profile37 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("지한")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile37.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("심리학과"))
                                .MBTI(MBTI.ESTJ)
                                .hobbies(Set.of(Hobby.YOGA, Hobby.HIKING))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("jihan_phone")
                                .build()
                );

                MemberProfile profile38 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("유빈")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile38.png")
                                .studentNumber(21)
                                .subMajor(subMajorRepository.findByNameOrderByName("국어국문학과"))
                                .MBTI(MBTI.ENFP)
                                .hobbies(Set.of(Hobby.FITNESS, Hobby.GARDENING))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("yubin_kakao")
                                .build()
                );

                MemberProfile profile39 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("민기")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile39.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("에너지공학과"))
                                .MBTI(MBTI.INTP)
                                .hobbies(Set.of(Hobby.CYCLING, Hobby.FITNESS))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("mingi_chat")
                                .build()
                );

                MemberProfile profile40 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("세영")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile40.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("의학과"))
                                .MBTI(MBTI.ISFP)
                                .hobbies(Set.of(Hobby.SWIMMING, Hobby.YOGA))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("seyoung_phone")
                                .build()
                );
                // MemberProfile 41~50 추가
                MemberProfile profile41 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("도윤1")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile41.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("기계공학과"))
                                .MBTI(MBTI.ENFP)
                                .hobbies(Set.of(Hobby.HIKING, Hobby.FITNESS))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("doyun_kakao")
                                .build()
                );

                MemberProfile profile42 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("하늘")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile42.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                                .MBTI(MBTI.ISTJ)
                                .hobbies(Set.of(Hobby.PUZZLE_SOLVING, Hobby.READING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("haneul_chat")
                                .build()
                );

                MemberProfile profile43 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("태희")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile43.png")
                                .studentNumber(21)
                                .subMajor(subMajorRepository.findByNameOrderByName("정보보안학과"))
                                .MBTI(MBTI.INTP)
                                .hobbies(Set.of(Hobby.GAMING, Hobby.COOKING))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("taehee_kakao")
                                .build()
                );

                MemberProfile profile44 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("주연")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile44.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("심리학과"))
                                .MBTI(MBTI.INFP)
                                .hobbies(Set.of(Hobby.TRAVELING, Hobby.YOGA))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("jooyeon_phone")
                                .build()
                );

                MemberProfile profile45 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("재민")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile45.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("전기전자공학과"))
                                .MBTI(MBTI.ESTP)
                                .hobbies(Set.of(Hobby.BADMINTON, Hobby.TENNIS))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("jaemin_chat")
                                .build()
                );

                MemberProfile profile46 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("유나1")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile46.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("교육학과"))
                                .MBTI(MBTI.ENFJ)
                                .hobbies(Set.of(Hobby.PAINTING, Hobby.VIOLIN))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("yuna_kakao")
                                .build()
                );

                MemberProfile profile47 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("수혁")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile47.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("경제학과"))
                                .MBTI(MBTI.ENTJ)
                                .hobbies(Set.of(Hobby.CYCLING, Hobby.YOGA))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("soohyeok_phone")
                                .build()
                );

                MemberProfile profile48 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("도진")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile48.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("환경공학과"))
                                .MBTI(MBTI.INTJ)
                                .hobbies(Set.of(Hobby.HIKING, Hobby.CAMPING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("dojin_chat")
                                .build()
                );

                MemberProfile profile49 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("예진")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile49.png")
                                .studentNumber(21)
                                .subMajor(subMajorRepository.findByNameOrderByName("한의학과"))
                                .MBTI(MBTI.ISFP)
                                .hobbies(Set.of(Hobby.WRITING, Hobby.LISTENING_TO_MUSIC))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("yejin_phone")
                                .build()
                );

                MemberProfile profile50 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("승준")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile50.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("약학과"))
                                .MBTI(MBTI.ESFP)
                                .hobbies(Set.of(Hobby.TRAVELING, Hobby.SOCCER))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("seungjun_kakao")
                                .build()
                );
                // MemberProfile 51~60 추가
                MemberProfile profile51 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("성민")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile51.png")
                                .studentNumber(21)
                                .subMajor(subMajorRepository.findByNameOrderByName("기계공학과"))
                                .MBTI(MBTI.ISTP)
                                .hobbies(Set.of(Hobby.GAMING, Hobby.CYCLING))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("seongmin_kakao")
                                .build()
                );

                MemberProfile profile52 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("수진12")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile52.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("정보보안학과"))
                                .MBTI(MBTI.ENFP)
                                .hobbies(Set.of(Hobby.DEVELOPMENT, Hobby.READING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("sujin_chat")
                                .build()
                );

                MemberProfile profile53 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("정훈")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile53.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("전기전자공학과"))
                                .MBTI(MBTI.ENTJ)
                                .hobbies(Set.of(Hobby.HIKING, Hobby.PUZZLE_SOLVING))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("junghoon_phone")
                                .build()
                );

                MemberProfile profile54 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("다연")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile54.png")
                                .studentNumber(20)
                                .subMajor(subMajorRepository.findByNameOrderByName("문헌정보학과"))
                                .MBTI(MBTI.INFJ)
                                .hobbies(Set.of(Hobby.WRITING, Hobby.LISTENING_TO_MUSIC))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("dayeon_kakao")
                                .build()
                );

                MemberProfile profile55 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("현우2")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile55.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("사회학과"))
                                .MBTI(MBTI.ESTP)
                                .hobbies(Set.of(Hobby.COOKING, Hobby.GAMING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("hyunwoo_chat")
                                .build()
                );

                MemberProfile profile56 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("소희")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile56.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("간호학과"))
                                .MBTI(MBTI.ISFJ)
                                .hobbies(Set.of(Hobby.GARDENING, Hobby.YOGA))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("sohee_phone")
                                .build()
                );

                MemberProfile profile57 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("태윤")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile57.png")
                                .studentNumber(21)
                                .subMajor(subMajorRepository.findByNameOrderByName("화학공학과"))
                                .MBTI(MBTI.INTP)
                                .hobbies(Set.of(Hobby.GAMING, Hobby.DEVELOPMENT))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("taeyun_chat")
                                .build()
                );

                MemberProfile profile58 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("지원")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile58.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("관광학과"))
                                .MBTI(MBTI.ENFJ)
                                .hobbies(Set.of(Hobby.TRAVELING, Hobby.CYCLING))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("jiwon_kakao")
                                .build()
                );

                MemberProfile profile59 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("은채")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile59.png")
                                .studentNumber(20)
                                .subMajor(subMajorRepository.findByNameOrderByName("철학과"))
                                .MBTI(MBTI.INFP)
                                .hobbies(Set.of(Hobby.READING, Hobby.HANDMADE))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("eunchae_phone")
                                .build()
                );

                MemberProfile profile60 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("도현")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile60.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("행정학과"))
                                .MBTI(MBTI.ESTJ)
                                .hobbies(Set.of(Hobby.BADMINTON, Hobby.SWIMMING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("dohyun_chat")
                                .build()
                );
                // MateType이 MEAL("혼밥")인 MemberProfile 71~85 생성
                MemberProfile profile61 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("민호")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile71.png")
                                .studentNumber(21)
                                .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                                .MBTI(MBTI.ISTJ)
                                .hobbies(Set.of(Hobby.COOKING, Hobby.READING))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("minho_kakao")
                                .build()
                );

                MemberProfile profile62 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("수진")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile72.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("심리학과"))
                                .MBTI(MBTI.INFP)
                                .hobbies(Set.of(Hobby.TRAVELING, Hobby.YOGA))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("sujin_phone")
                                .build()
                );

                MemberProfile profile63 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("3지훈12")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile73.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("정보통신공학과"))
                                .MBTI(MBTI.ENTP)
                                .hobbies(Set.of(Hobby.GAMING, Hobby.CYCLING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("jihoon_chat")
                                .build()
                );

                MemberProfile profile64 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("유나")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile74.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("화학과"))
                                .MBTI(MBTI.ESFJ)
                                .hobbies(Set.of(Hobby.COOKING, Hobby.LISTENING_TO_MUSIC))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("yuna_phone")
                                .build()
                );

                MemberProfile profile65 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("경수")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile75.png")
                                .studentNumber(20)
                                .subMajor(subMajorRepository.findByNameOrderByName("기계공학과"))
                                .MBTI(MBTI.ENTJ)
                                .hobbies(Set.of(Hobby.BADMINTON, Hobby.HIKING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("kyungsoo_chat")
                                .build()
                );

                MemberProfile profile66 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("소연")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile76.png")
                                .studentNumber(21)
                                .subMajor(subMajorRepository.findByNameOrderByName("국어국문학과"))
                                .MBTI(MBTI.ISFP)
                                .hobbies(Set.of(Hobby.WRITING, Hobby.LISTENING_TO_MUSIC))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("soyeon_kakao")
                                .build()
                );

                MemberProfile profile67 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("준혁")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile77.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("약학과"))
                                .MBTI(MBTI.INTJ)
                                .hobbies(Set.of(Hobby.READING, Hobby.CYCLING))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("junhyuk_phone")
                                .build()
                );

                MemberProfile profile68 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("나연2")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile78.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("전기전자공학과"))
                                .MBTI(MBTI.ENFP)
                                .hobbies(Set.of(Hobby.PAINTING, Hobby.LISTENING_TO_MUSIC))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("nayeon_chat")
                                .build()
                );

                MemberProfile profile69 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("영우")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile79.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("수학과"))
                                .MBTI(MBTI.ESTP)
                                .hobbies(Set.of(Hobby.SWIMMING, Hobby.TENNIS))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("youngwoo_kakao")
                                .build()
                );

                MemberProfile profile70 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("지연")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile80.png")
                                .studentNumber(20)
                                .subMajor(subMajorRepository.findByNameOrderByName("사회학과"))
                                .MBTI(MBTI.ISFJ)
                                .hobbies(Set.of(Hobby.COOKING,Hobby.HANDMADE))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("jiyeon_phone")
                                .build()
                );

                MemberProfile profile71 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("태민1")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile81.png")
                                .studentNumber(21)
                                .subMajor(subMajorRepository.findByNameOrderByName("교육학과"))
                                .MBTI(MBTI.ISTP)
                                .hobbies(Set.of(Hobby.PAINTING, Hobby.LISTENING_TO_MUSIC))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("taemin_phone")
                                .build()
                );

                MemberProfile profile72 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("혜린11")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile82.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("경제학과"))
                                .MBTI(MBTI.ENTP)
                                .hobbies(Set.of(Hobby.COOKING, Hobby.YOGA))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("hyerin_chat")
                                .build()
                );

                MemberProfile profile73 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("도윤")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile83.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("간호학과"))
                                .MBTI(MBTI.INFJ)
                                .hobbies(Set.of(Hobby.GAMING, Hobby.HIKING))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("doyun_kakao")
                                .build()
                );

                MemberProfile profile74 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("수아")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile84.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("행정학과"))
                                .MBTI(MBTI.ESTJ)
                                .hobbies(Set.of(Hobby.SWIMMING, Hobby.LISTENING_TO_MUSIC))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("sua_phone")
                                .build()
                );

                MemberProfile profile75 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("현우")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile85.png")
                                .studentNumber(20)
                                .subMajor(subMajorRepository.findByNameOrderByName("건축학과"))
                                .MBTI(MBTI.ESFP)
                                .hobbies(Set.of(Hobby.TRAVELING, Hobby.GARDENING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("hyunwoo_chat")
                                .build()
                );

                MemberProfile profile76 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("윤서")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile76.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("건축학과"))
                                .MBTI(MBTI.INFP)
                                .hobbies(Set.of(Hobby.PAINTING, Hobby.COOKING))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("yunseo_kakao")
                                .build()
                );

                MemberProfile profile77 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("성진")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile77.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("정보통신공학과"))
                                .MBTI(MBTI.ENTP)
                                .hobbies(Set.of(Hobby.GAMING, Hobby.READING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("seongjin_chat")
                                .build()
                );

                MemberProfile profile78 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("다은")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile78.png")
                                .studentNumber(21)
                                .subMajor(subMajorRepository.findByNameOrderByName("수학과"))
                                .MBTI(MBTI.ISTJ)
                                .hobbies(Set.of(Hobby.PUZZLE_SOLVING, Hobby.CYCLING))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("daeun_phone")
                                .build()
                );

                MemberProfile profile79 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("지훈")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile79.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("전기전자공학과"))
                                .MBTI(MBTI.ENFP)
                                .hobbies(Set.of(Hobby.TRAVELING, Hobby.FITNESS))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("jihoon_chat")
                                .build()
                );

                MemberProfile profile80 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("서현")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile80.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("심리학과"))
                                .MBTI(MBTI.ESFP)
                                .hobbies(Set.of(Hobby.LISTENING_TO_MUSIC, Hobby.WRITING))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("seohyun_kakao")
                                .build()
                );

                MemberProfile profile81 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("동현")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile81.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("간호학과"))
                                .MBTI(MBTI.ISTP)
                                .hobbies(Set.of(Hobby.HIKING, Hobby.COOKING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("donghyun_chat")
                                .build()
                );

                MemberProfile profile82 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("은지")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile82.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("경제학과"))
                                .MBTI(MBTI.ENFJ)
                                .hobbies(Set.of(Hobby.YOGA))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("eunji_phone")
                                .build()
                );

                MemberProfile profile83 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("태민")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile83.png")
                                .studentNumber(25)
                                .subMajor(subMajorRepository.findByNameOrderByName("경영학과"))
                                .MBTI(MBTI.INTP)
                                .hobbies(Set.of(Hobby.READING, Hobby.PUZZLE_SOLVING))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("taemin_chat")
                                .build()
                );

                MemberProfile profile84 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("소윤")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile84.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("사회학과"))
                                .MBTI(MBTI.ISFJ)
                                .hobbies(Set.of(Hobby.COOKING))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("soyun_kakao")
                                .build()
                );

                MemberProfile profile85 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("현준")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile85.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("화학공학과"))
                                .MBTI(MBTI.ESTJ)
                                .hobbies(Set.of(Hobby.LISTENING_TO_MUSIC, Hobby.TRAVELING))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("hyunjun_phone")
                                .build()
                );

                MemberProfile profile86 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("하연")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile86.png")
                                .studentNumber(24)
                                .subMajor(subMajorRepository.findByNameOrderByName("조경학과"))
                                .MBTI(MBTI.ENTP)
                                .hobbies(Set.of(Hobby.HIKING, Hobby.YOGA))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("hayeon_chat")
                                .build()
                );

                MemberProfile profile87 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("예린")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile87.png")
                                .studentNumber(21)
                                .subMajor(subMajorRepository.findByNameOrderByName("정치외교학과"))
                                .MBTI(MBTI.INFJ)
                                .hobbies(Set.of(Hobby.WRITING, Hobby.HIKING))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("yerin_kakao")
                                .build()
                );

                MemberProfile profile88 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("진우")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile88.png")
                                .studentNumber(25)
                                .subMajor(subMajorRepository.findByNameOrderByName("특수교육과"))
                                .MBTI(MBTI.ISFP)
                                .hobbies(Set.of(Hobby.TRAVELING, Hobby.FITNESS))
                                .contact(ContactType.PHONE_NUMBER)
                                .contactInfo("jinwoo_phone")
                                .build()
                );

                MemberProfile profile89 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("승호")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile89.png")
                                .studentNumber(22)
                                .subMajor(subMajorRepository.findByNameOrderByName("한약학과"))
                                .MBTI(MBTI.ESFJ)
                                .hobbies(Set.of(Hobby.COOKING, Hobby.BADMINTON))
                                .contact(ContactType.OPEN_CHAT_LINK)
                                .contactInfo("seungho_chat")
                                .build()
                );

                MemberProfile profile90 = memberProfileRepository.save(
                        MemberProfile.builder()
                                .nickname("나연")
                                .profileImage("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/profile90.png")
                                .studentNumber(23)
                                .subMajor(subMajorRepository.findByNameOrderByName("교육학과"))
                                .MBTI(MBTI.ISTP)
                                .hobbies(Set.of(Hobby.READING, Hobby.LISTENING_TO_MUSIC))
                                .contact(ContactType.KAKAO_TALK_ID)
                                .contactInfo("nayoun_kakao")
                                .build()
                );

                memberProfileRepository.save(profile1);
                memberProfileRepository.save(profile2);
                memberProfileRepository.save(profile3);
                memberProfileRepository.save(profile4);
                memberProfileRepository.save(profile5);
                memberProfileRepository.save(profile6);
                memberProfileRepository.save(profile7);
                memberProfileRepository.save(profile8);
                memberProfileRepository.save(profile9);
                memberProfileRepository.save(profile10);
                memberProfileRepository.save(profile11);
                memberProfileRepository.save(profile12);
                memberProfileRepository.save(profile13);
                memberProfileRepository.save(profile14);
                memberProfileRepository.save(profile15);
                memberProfileRepository.save(profile16);
                memberProfileRepository.save(profile17);
                memberProfileRepository.save(profile18);
                memberProfileRepository.save(profile19);
                memberProfileRepository.save(profile20);
                memberProfileRepository.save(profile21);
                memberProfileRepository.save(profile22);
                memberProfileRepository.save(profile23);
                memberProfileRepository.save(profile24);
                memberProfileRepository.save(profile25);
                memberProfileRepository.save(profile26);
                memberProfileRepository.save(profile27);
                memberProfileRepository.save(profile28);
                memberProfileRepository.save(profile29);
                memberProfileRepository.save(profile30);
                memberProfileRepository.save(profile31);
                memberProfileRepository.save(profile32);
                memberProfileRepository.save(profile33);
                memberProfileRepository.save(profile34);
                memberProfileRepository.save(profile35);
                memberProfileRepository.save(profile36);
                memberProfileRepository.save(profile37);
                memberProfileRepository.save(profile38);
                memberProfileRepository.save(profile39);
                memberProfileRepository.save(profile40);
                 memberProfileRepository.save(profile41);
                memberProfileRepository.save(profile42);
                memberProfileRepository.save(profile43);
                memberProfileRepository.save(profile44);
                memberProfileRepository.save(profile45);
                memberProfileRepository.save(profile46);
                memberProfileRepository.save(profile47);
                memberProfileRepository.save(profile48);
                memberProfileRepository.save(profile49);
                memberProfileRepository.save(profile50);
                memberProfileRepository.save(profile51);
                memberProfileRepository.save(profile52);
                memberProfileRepository.save(profile53);
                memberProfileRepository.save(profile54);
                memberProfileRepository.save(profile55);
                memberProfileRepository.save(profile56);
                memberProfileRepository.save(profile57);
                memberProfileRepository.save(profile58);
                memberProfileRepository.save(profile59);
                memberProfileRepository.save(profile60);
                memberProfileRepository.save(profile61);
                memberProfileRepository.save(profile62);
                memberProfileRepository.save(profile63);
                memberProfileRepository.save(profile64);
                memberProfileRepository.save(profile65);
                memberProfileRepository.save(profile66);
                memberProfileRepository.save(profile67);
                memberProfileRepository.save(profile68);
                memberProfileRepository.save(profile69);
                memberProfileRepository.save(profile70);
                memberProfileRepository.save(profile71);
                memberProfileRepository.save(profile72);
                memberProfileRepository.save(profile73);
                memberProfileRepository.save(profile74);
                memberProfileRepository.save(profile75);
                memberProfileRepository.save(profile76);
                memberProfileRepository.save(profile77);
                memberProfileRepository.save(profile78);
                memberProfileRepository.save(profile79);
                memberProfileRepository.save(profile80);
                memberProfileRepository.save(profile81);
                memberProfileRepository.save(profile82);
                memberProfileRepository.save(profile83);
                memberProfileRepository.save(profile84);
                memberProfileRepository.save(profile85);
                memberProfileRepository.save(profile86);
                memberProfileRepository.save(profile87);
                memberProfileRepository.save(profile88);
                memberProfileRepository.save(profile89);
                 memberProfileRepository.save(profile90);
                memberRepository.flush();
                // 2. Member 생성 (MemberProfile 포함)
                Member member1 = Member.builder()
                        .name("윤빈")
                        .gender(Gender.FEMALE)
                        .birthday(sdf.parse("2000-12-31"))
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
                        .birthday(sdf.parse("2003-12-31"))
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
                        .birthday(sdf.parse("2000-12-31"))
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
                        .birthday(sdf.parse("2002-4-15"))
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
                        .birthday(sdf.parse("2001-12-31"))
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
                        .birthday(sdf.parse("2001-12-31"))
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
                        .birthday(sdf.parse("2003-12-31"))
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
                        .birthday(sdf.parse("2004-12-31"))
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
                        .birthday(sdf.parse("2003-12-31"))
                        .university(University.SUNGSHIN_WOMANS_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(9L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile9)
                        .isVerified(true)
                        .build();

                Member member10 = Member.builder()
                        .name("김지원")
                        .gender(Gender.FEMALE)
                        .birthday(sdf.parse("2002-9-21"))
                        .university(University.SUNGKYUNKWAN_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(15L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile10)
                        .isVerified(true)
                        .build();

                Member member11 = Member.builder()
                        .name("한서연")
                        .gender(Gender.FEMALE)
                        .birthday(sdf.parse("2001-7-14"))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(20L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile11)
                        .isVerified(true)
                        .build();

                Member member12 = Member.builder()
                        .name("박지수")
                        .gender(Gender.FEMALE)
                        .birthday(sdf.parse("2002-10-11"))
                        .university(University.HONGIK_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(21L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile12)
                        .isVerified(true)
                        .build();

                Member member13 = Member.builder()
                        .name("이민수")
                        .gender(Gender.MALE)
                        .birthday(sdf.parse("2001-6-25"))
                        .university(University.SOGANG_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(22L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile13)
                        .isVerified(true)
                        .build();

                Member member14 = Member.builder()
                        .name("최도현")
                        .gender(Gender.MALE)
                        .birthday(sdf.parse("1999-4-10"))
                        .university(University.KOREA_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(36L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile14)
                        .isVerified(true)
                        .build();

                Member member15 = Member.builder()
                        .name("김윤아")
                        .gender(Gender.FEMALE)
                        .birthday(sdf.parse("2001-9-10"))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(40L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile15)
                        .isVerified(true)
                        .build();

                Member member16 = Member.builder()
                        .name("박지훈")
                        .gender(Gender.MALE)
                        .birthday(sdf.parse("2001-2-20"))
                        .university(University.YONSEI_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(37L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile16)
                        .isVerified(true)
                        .build();

                Member member17 = Member.builder()
                        .name("김수진")
                        .gender(Gender.FEMALE)
                        .birthday(sdf.parse("2000-12-5"))
                        .university(University.SEOUL_NATIONAL_UNIVERSITY)
                        .socialType(SocialType.KAKAO)
                        .socialId(38L)
                        .status(MemberStatus.ACTIVE)
                        .role(MemberRole.MEMBER)
                        .memberProfile(profile17)
                        .isVerified(true)
                        .build();
                Member member18 = memberRepository.save(
                        Member.builder()
                                .name("김도경")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2001-5-10"))
                                .university(University.HANYANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(50L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile18)
                                .isVerified(true)
                                .build()
                );

                Member member19 = memberRepository.save(
                        Member.builder()
                                .name("박은지")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-3-20"))
                                .university(University.EWHA_WOMANS_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(51L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile19)
                                .isVerified(true)
                                .build()
                );

                Member member20 = memberRepository.save(
                        Member.builder()
                                .name("이하영")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2000-7-15"))
                                .university(University.KOREA_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(52L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile20)
                                .isVerified(true)
                                .build()
                );

                Member member21 = memberRepository.save(
                        Member.builder()
                                .name("최석민")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("1999-11-22"))
                                .university(University.SEOUL_NATIONAL_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(53L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile21)
                                .isVerified(true)
                                .build()
                );

                Member member22 = memberRepository.save(
                        Member.builder()
                                .name("김혜린")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2003-2-28"))
                                .university(University.SUNGKYUNKWAN_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(54L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile22)
                                .isVerified(true)
                                .build()
                );

                Member member23 = memberRepository.save(
                        Member.builder()
                                .name("최진우")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-6-30"))
                                .university(University.SOGANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(55L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile23)
                                .isVerified(true)
                                .build()
                );

                Member member24 = memberRepository.save(
                        Member.builder()
                                .name("장다혜")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-9-12"))
                                .university(University.YONSEI_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(56L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile24)
                                .isVerified(true)
                                .build()
                );

                Member member25 = memberRepository.save(
                        Member.builder()
                                .name("이민혁")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2000-2-18"))
                                .university(University.HANKUK_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(57L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile25)
                                .isVerified(true)
                                .build()
                );

                Member member26 = memberRepository.save(
                        Member.builder()
                                .name("강서진")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2001-10-5"))
                                .university(University.CHUNGANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(58L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile26)
                                .isVerified(true)
                                .build()
                );

                Member member27 = memberRepository.save(
                        Member.builder()
                                .name("이지훈")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("1999-4-22"))
                                .university(University.DONGGUK_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(59L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile27)
                                .isVerified(true)
                                .build()
                );

                Member member28 = memberRepository.save(
                        Member.builder()
                                .name("손채영")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2001-8-17"))
                                .university(University.SEOUL_NATIONAL_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(60L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile28)
                                .isVerified(true)
                                .build()
                );
                Member member29 = memberRepository.save(
                        Member.builder()
                                .name("김정민")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-3-15"))
                                .university(University.KOREA_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(61L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile29)
                                .isVerified(true)
                                .build()
                );

                Member member30 = memberRepository.save(
                        Member.builder()
                                .name("박예린")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-11-9"))
                                .university(University.SOGANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(62L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile30)
                                .isVerified(true)
                                .build()
                );
                Member member31 = memberRepository.save(
                        Member.builder()
                                .name("강준영")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-5-14"))
                                .university(University.YONSEI_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(61L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile31)
                                .isVerified(true)
                                .build()
                );

                Member member32 = memberRepository.save(
                        Member.builder()
                                .name("김지수")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-8-20"))
                                .university(University.SEOUL_NATIONAL_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(62L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile32)
                                .isVerified(true)
                                .build()
                );

                Member member33 = memberRepository.save(
                        Member.builder()
                                .name("박윤호")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2000-11-10"))
                                .university(University.KOREA_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(63L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile33)
                                .isVerified(true)
                                .build()
                );

                Member member34 = memberRepository.save(
                        Member.builder()
                                .name("이채린")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2001-9-25"))
                                .university(University.HANYANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(64L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile34)
                                .isVerified(true)
                                .build()
                );

                Member member35 = memberRepository.save(
                        Member.builder()
                                .name("정형준")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-6-30"))
                                .university(University.SUNGKYUNKWAN_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(65L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile35)
                                .isVerified(true)
                                .build()
                );

                Member member36 = memberRepository.save(
                        Member.builder()
                                .name("문다영")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-4-11"))
                                .university(University.CHUNGANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(66L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile36)
                                .isVerified(true)
                                .build()
                );

                Member member37 = memberRepository.save(
                        Member.builder()
                                .name("서지한")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2000-7-18"))
                                .university(University.DONGGUK_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(67L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile37)
                                .isVerified(true)
                                .build()
                );

                Member member38 = memberRepository.save(
                        Member.builder()
                                .name("안유빈")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2003-1-22"))
                                .university(University.EWHA_WOMANS_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(68L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile38)
                                .isVerified(true)
                                .build()
                );

                Member member39 = memberRepository.save(
                        Member.builder()
                                .name("조민기")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-3-29"))
                                .university(University.KYUNGHEE_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(69L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile39)
                                .isVerified(true)
                                .build()
                );

                Member member40 = memberRepository.save(
                        Member.builder()
                                .name("한세영")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-12-7"))
                                .university(University.SEOUL_NATIONAL_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(70L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile40)
                                .isVerified(true)
                                .build()
                );
                // Member 41~50 추가
                Member member41 = memberRepository.save(
                        Member.builder()
                                .name("이도윤")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2002-5-10"))
                                .university(University.KOREA_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(71L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile41)
                                .isVerified(true)
                                .build()
                );

                Member member42 = memberRepository.save(
                        Member.builder()
                                .name("김하늘")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2001-9-15"))
                                .university(University.YONSEI_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(72L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile42)
                                .isVerified(true)
                                .build()
                );

                Member member43 = memberRepository.save(
                        Member.builder()
                                .name("박태희")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2000-3-8"))
                                .university(University.SEOUL_NATIONAL_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(73L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile43)
                                .isVerified(true)
                                .build()
                );

                Member member44 = memberRepository.save(
                        Member.builder()
                                .name("조주연")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-11-3"))
                                .university(University.EWHA_WOMANS_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(74L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile44)
                                .isVerified(true)
                                .build()
                );

                Member member45 = memberRepository.save(
                        Member.builder()
                                .name("한재민")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-7-22"))
                                .university(University.HANKUK_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(75L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile45)
                                .isVerified(true)
                                .build()
                );

                Member member46 = memberRepository.save(
                        Member.builder()
                                .name("오유나")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2003-2-14"))
                                .university(University.CHUNGANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(76L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile46)
                                .isVerified(true)
                                .build()
                );

                Member member47 = memberRepository.save(
                        Member.builder()
                                .name("정수혁")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2000-6-25"))
                                .university(University.DONGGUK_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(77L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile47)
                                .isVerified(true)
                                .build()
                );

                Member member48 = memberRepository.save(
                        Member.builder()
                                .name("신도진")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-4-12"))
                                .university(University.KOREA_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(78L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile48)
                                .isVerified(true)
                                .build()
                );

                Member member49 = memberRepository.save(
                        Member.builder()
                                .name("배예진")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-8-19"))
                                .university(University.SUNGKYUNKWAN_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(79L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile49)
                                .isVerified(true)
                                .build()
                );

                Member member50 = memberRepository.save(
                        Member.builder()
                                .name("강승준")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-12-30"))
                                .university(University.HANKUK_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(80L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile50)
                                .isVerified(true)
                                .build()
                );
                // Member 51~60 추가
                Member member51 = memberRepository.save(
                        Member.builder()
                                .name("김성민")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2002-5-13"))
                                .university(University.KOREA_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(91L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile51)
                                .isVerified(true)
                                .build()
                );

                Member member52 = memberRepository.save(
                        Member.builder()
                                .name("박수진")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2001-8-24"))
                                .university(University.YONSEI_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(92L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile52)
                                .isVerified(true)
                                .build()
                );

                Member member53 = memberRepository.save(
                        Member.builder()
                                .name("최정훈")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2000-10-30"))
                                .university(University.SEOUL_NATIONAL_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(93L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile53)
                                .isVerified(true)
                                .build()
                );

                Member member54 = memberRepository.save(
                        Member.builder()
                                .name("이다연")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2003-2-18"))
                                .university(University.CHUNGANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(94L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile54)
                                .isVerified(true)
                                .build()
                );

                Member member55 = memberRepository.save(
                        Member.builder()
                                .name("정현우")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("1999-11-5"))
                                .university(University.CITY_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(95L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile55)
                                .isVerified(true)
                                .build()
                );

                Member member56 = memberRepository.save(
                        Member.builder()
                                .name("배소희")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2001-6-20"))
                                .university(University.EWHA_WOMANS_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(96L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile56)
                                .isVerified(true)
                                .build()
                );

                Member member57 = memberRepository.save(
                        Member.builder()
                                .name("윤태윤")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2002-4-12"))
                                .university(University.KOREA_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(97L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile57)
                                .isVerified(true)
                                .build()
                );

                Member member58 = memberRepository.save(
                        Member.builder()
                                .name("장지원")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2003-9-3"))
                                .university(University.HANKUK_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(98L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                //.memberProfile(profile58)
                                .isVerified(true)
                                .build()
                );

                Member member59 = memberRepository.save(
                        Member.builder()
                                .name("김은채")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2000-7-8"))
                                .university(University.DONGGUK_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(99L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile59)
                                .isVerified(true)
                                .build()
                );

                Member member60 = memberRepository.save(
                        Member.builder()
                                .name("한도현")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("1999-12-27"))
                                .university(University.SEOUL_NATIONAL_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(100L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile60)
                                .isVerified(true)
                                .build()
                );
                Member member61 = memberRepository.save(
                        Member.builder()
                                .name("이민재")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-7-10"))
                                .university(University.SEOUL_NATIONAL_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(101L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile61)
                                .isVerified(true)
                                .build()
                );

                Member member62 = memberRepository.save(
                        Member.builder()
                                .name("박지윤")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-3-25"))
                                .university(University.KOREA_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(102L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile62)
                                .isVerified(true)
                                .build()
                );

                Member member63 = memberRepository.save(
                        Member.builder()
                                .name("김태우")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2002-12-18"))
                                .university(University.YONSEI_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(103L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile63)
                                .isVerified(true)
                                .build()
                );

                Member member64 = memberRepository.save(
                        Member.builder()
                                .name("정수빈")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2000-8-8"))
                                .university(University.SUNGKYUNKWAN_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(104L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile64)
                                .isVerified(true)
                                .build()
                );

                Member member65 = memberRepository.save(
                        Member.builder()
                                .name("최재원")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-11-21"))
                                .university(University.HANYANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(105L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile65)
                                .isVerified(true)
                                .build()
                );

                Member member66 = memberRepository.save(
                        Member.builder()
                                .name("오지현")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2003-9-4"))
                                .university(University.SOONGSHIL_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(106L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile66)
                                .isVerified(true)
                                .build()
                );

                Member member67 = memberRepository.save(
                        Member.builder()
                                .name("류시훈")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2000-5-30"))
                                .university(University.YONSEI_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(107L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile67)
                                .isVerified(true)
                                .build()
                );

                Member member68 = memberRepository.save(
                        Member.builder()
                                .name("강다은")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2001-2-13"))
                                .university(University.CHUNGGYE_WOMANS_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(108L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile68)
                                .isVerified(true)
                                .build()
                );

                Member member69 = memberRepository.save(
                        Member.builder()
                                .name("송민준")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2002-6-22"))
                                .university(University.KYUNGHEE_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(109L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile69)
                                .isVerified(true)
                                .build()
                );

                Member member70 = memberRepository.save(
                        Member.builder()
                                .name("김나영")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("1999-1-10"))
                                .university(University.CHUNGANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(110L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile70)
                                .isVerified(true)
                                .build()
                );

                Member member71 = memberRepository.save(
                        Member.builder()
                                .name("이준석")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2000-5-15"))
                                .university(University.DONGGUK_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(111L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile71)
                                .isVerified(true)
                                .build()
                );

                Member member72 = memberRepository.save(
                        Member.builder()
                                .name("배수진")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("1998-9-7"))
                                .university(University.HONGIK_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(112L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile72)
                                .isVerified(true)
                                .build()
                );

                Member member73 = memberRepository.save(
                        Member.builder()
                                .name("한서준")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-2-28"))
                                .university(University.KUNKUK_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(113L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile73)
                                .isVerified(true)
                                .build()
                );

                Member member74 = memberRepository.save(
                        Member.builder()
                                .name("조유리")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-11-3"))
                                .university(University.SOGANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(114L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile74)
                                .isVerified(true)
                                .build()
                );

                Member member75 = memberRepository.save(
                        Member.builder()
                                .name("서지훈")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2003-4-20"))
                                .university(University.KYUNGIN_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(115L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile75)
                                .isVerified(true)
                                .build()
                );
                Member member76 = memberRepository.save(
                        Member.builder()
                                .name("이윤서")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-3-15"))
                                .university(University.KOREA_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(111L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile76)
                                .isVerified(true)
                                .build()
                );

                Member member77 = memberRepository.save(
                        Member.builder()
                                .name("함성진")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-9-28"))
                                .university(University.YONSEI_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(112L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile77)
                                .isVerified(true)
                                .build()
                );

                Member member78 = memberRepository.save(
                        Member.builder()
                                .name("성다은")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2003-6-7"))
                                .university(University.SUNGKYUNKWAN_UNIVERSITY)
                                .socialType(SocialType.GOOGLE)
                                .socialId(113L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile78)
                                .isVerified(true)
                                .build()
                );

                Member member79 = memberRepository.save(
                        Member.builder()
                                .name("김지훈")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2000-11-3"))
                                .university(University.HANYANG_UNIVERSITY)
                                .socialType(SocialType.FACEBOOK)
                                .socialId(114L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile79)
                                .isVerified(true)
                                .build()
                );

                Member member80 = memberRepository.save(
                        Member.builder()
                                .name("최서현")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-8-21"))
                                .university(University.SEOUL_NATIONAL_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(115L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile80)
                                .isVerified(true)
                                .build()
                );

                Member member81 = memberRepository.save(
                        Member.builder()
                                .name("원동현")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-5-12"))
                                .university(University.HANYANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(116L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile81)
                                .isVerified(true)
                                .build()
                );

                Member member82 = memberRepository.save(
                        Member.builder()
                                .name("이은지")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2003-4-9"))
                                .university(University.SAMYOOK_UNIVERSITY)
                                .socialType(SocialType.GOOGLE)
                                .socialId(117L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile82)
                                .isVerified(true)
                                .build()
                );

                Member member83 = memberRepository.save(
                        Member.builder()
                                .name("김태민")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2002-7-14"))
                                .university(University.SOGANG_UNIVERSITY)
                                .socialType(SocialType.FACEBOOK)
                                .socialId(118L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile83)
                                .isVerified(true)
                                .build()
                );

                Member member84 = memberRepository.save(
                        Member.builder()
                                .name("이소윤")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2001-1-5"))
                                .university(University.CHUNGANG_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(119L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile84)
                                .isVerified(true)
                                .build()
                );

                Member member85 = memberRepository.save(
                        Member.builder()
                                .name("방현준")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2003-10-30"))
                                .university(University.SOONGSHIL_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(120L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile85)
                                .isVerified(true)
                                .build()
                );

                Member member86 = memberRepository.save(
                        Member.builder()
                                .name("김하연")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2000-11-18"))
                                .university(University.KOREA_UNIVERSITY)
                                .socialType(SocialType.GOOGLE)
                                .socialId(121L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile86)
                                .isVerified(true)
                                .build()
                );

                Member member87 = memberRepository.save(
                        Member.builder()
                                .name("박예린")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2002-2-26"))
                                .university(University.YONSEI_UNIVERSITY)
                                .socialType(SocialType.FACEBOOK)
                                .socialId(122L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile87)
                                .isVerified(true)
                                .build()
                );

                Member member88 = memberRepository.save(
                        Member.builder()
                                .name("김진우")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2001-6-1"))
                                .university(University.SUNGKYUNKWAN_UNIVERSITY)
                                .socialType(SocialType.KAKAO)
                                .socialId(123L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile88)
                                .isVerified(true)
                                .build()
                );

                Member member89 = memberRepository.save(
                        Member.builder()
                                .name("신승호")
                                .gender(Gender.MALE)
                                .birthday(sdf.parse("2003-9-24"))
                                .university(University.HANYANG_UNIVERSITY)
                                .socialType(SocialType.GOOGLE)
                                .socialId(124L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                .memberProfile(profile89)
                                .isVerified(true)
                                .build()
                );

                Member member90 = memberRepository.save(
                        Member.builder()
                                .name("이나연")
                                .gender(Gender.FEMALE)
                                .birthday(sdf.parse("2000-7-8"))
                                .university(University.SEOUL_NATIONAL_UNIVERSITY)
                                .socialType(SocialType.GOOGLE)
                                .socialId(125L)
                                .status(MemberStatus.ACTIVE)
                                .role(MemberRole.MEMBER)
                                //.memberProfile(profile90)
                                .isVerified(true)
                                .build()
                );

                memberRepository.save(member1);
                memberRepository.save(member2);
                memberRepository.save(member3);
                memberRepository.save(member4);
                memberRepository.save(member5);
                memberRepository.save(member6);
                memberRepository.save(member7);
                memberRepository.save(member8);
                memberRepository.save(member9);
                memberRepository.save(member10);
                memberRepository.save(member11);
                memberRepository.save(member12);
                memberRepository.save(member13);
                memberRepository.save(member14);
                memberRepository.save(member15);
                memberRepository.save(member16);
                memberRepository.save(member17);
                memberRepository.save(member18);
                memberRepository.save(member19);
                memberRepository.save(member20);
                memberRepository.save(member21);
                memberRepository.save(member22);
                memberRepository.save(member23);
                memberRepository.save(member24);
                memberRepository.save(member25);
                memberRepository.save(member26);
                memberRepository.save(member27);
                memberRepository.save(member28);
                memberRepository.save(member29);
                memberRepository.save(member30);
                memberRepository.save(member31);
                memberRepository.save(member32);
                memberRepository.save(member33);
                memberRepository.save(member34);
                memberRepository.save(member35);
                memberRepository.save(member36);
                memberRepository.save(member37);
                memberRepository.save(member38);
                memberRepository.save(member39);
                memberRepository.save(member40);
                memberRepository.save(member41);
                memberRepository.save(member42);
                memberRepository.save(member43);
                memberRepository.save(member44);
                memberRepository.save(member45);
                memberRepository.save(member46);
                memberRepository.save(member47);
                memberRepository.save(member48);
                memberRepository.save(member49);
                memberRepository.save(member50);
                memberRepository.save(member51);
                memberRepository.save(member52);
                memberRepository.save(member53);
                memberRepository.save(member54);
                memberRepository.save(member55);
                memberRepository.save(member56);
                memberRepository.save(member57);
                memberRepository.save(member58);
                memberRepository.save(member59);
                memberRepository.save(member60);
                memberRepository.save(member61);
                memberRepository.save(member62);
                memberRepository.save(member63);
                memberRepository.save(member64);
                memberRepository.save(member65);
                memberRepository.save(member66);
                memberRepository.save(member67);
                memberRepository.save(member68);
                memberRepository.save(member69);
                memberRepository.save(member70);
                memberRepository.save(member71);
                memberRepository.save(member72);
                memberRepository.save(member73);
                memberRepository.save(member74);
                memberRepository.save(member75);
                memberRepository.save(member76);
                memberRepository.save(member77);
                memberRepository.save(member78);
                memberRepository.save(member79);
                memberRepository.save(member80);
                memberRepository.save(member81);
                memberRepository.save(member82);
                memberRepository.save(member83);
                memberRepository.save(member84);
                memberRepository.save(member85);
                memberRepository.save(member86);
                memberRepository.save(member87);
                memberRepository.save(member88);
                memberRepository.save(member89);
                memberRepository.save(member90);

                memberRepository.flush();


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
                MemberSecondProfile secondProfile10 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member10)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(25)
                                .mbti(MBTI.INFP.name())
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("함께 공부할 팀원을 찾습니다!")
                                .studyType(StudyType.MAJOR)
                                .isSchool(true)
                                .majorName("인공지능학과")
                                .professorName("박교수님")
                                .isOnline(false)
                                .studyTimes(2)
                                .place("도서관")
                                .mateType(MateType.STUDY)
                                .build()
                );
                MemberSecondProfile secondProfile21 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member11)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(22)
                                .maxAge(28)
                                .mbti(MBTI.ENTJ.name())
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(5)
                                .currentPeople(2)
                                .comment("팀 프로젝트 함께할 분")
                                .studyType(StudyType.STUDY)
                                .isSchool(false)
                                .majorName("컴퓨터공학과")
                                .professorName("이교수님")
                                .isOnline(true)
                                .studyTimes(3)
                                .place("온라인")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile22 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member12)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(24)
                                .mbti(MBTI.INTP.name())
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("게임 기획 공부할 사람 찾습니다.")
                                .studyType(StudyType.MAJOR)
                                .isSchool(true)
                                .majorName("게임학과")
                                .professorName("김교수님")
                                .isOnline(false)
                                .studyTimes(2)
                                .place("스터디룸")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile23 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member13)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(23)
                                .maxAge(26)
                                .mbti(MBTI.ISTJ.name())
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("정보보안 논문 스터디 모집합니다.")
                                .studyType(StudyType.STUDY)
                                .isSchool(false)
                                .majorName("정보보안학과")
                                .professorName("박교수님")
                                .isOnline(true)
                                .studyTimes(3)
                                .place("온라인")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile24 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member14)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(23)
                                .maxAge(28)
                                .mbti(MBTI.ENFP.name())
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("건축 설계 스터디 모집합니다.")
                                .studyType(StudyType.STUDY)
                                .isSchool(false)
                                .majorName("건축공학과")
                                .professorName("이교수님")
                                .isOnline(true)
                                .studyTimes(3)
                                .place("온라인")
                                .mateType(MateType.STUDY)
                                .build()
                );
                MemberSecondProfile secondProfile25 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member15)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(22)
                                .maxAge(27)
                                .mbti(MBTI.ISTP.name())
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("전기전자공학 문제 풀이 및 프로젝트 팀원 구합니다.")
                                .studyType(StudyType.MAJOR)
                                .isSchool(true)
                                .majorName("전기전자공학과")
                                .professorName("박교수님")
                                .isOnline(false)
                                .studyTimes(3)
                                .place("공학관 스터디룸")
                                .mateType(MateType.STUDY)
                                .build()
                );
                MemberSecondProfile secondProfile26 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member16)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(22)
                                .maxAge(24)
                                .mbti(MBTI.INFP.name())
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(1)
                                .comment("심리학 연구 스터디 모집")
                                .studyType(StudyType.STUDY)
                                .isSchool(false)
                                .majorName("심리학과")
                                .professorName("박교수님")
                                .isOnline(true)
                                .studyTimes(2)
                                .place("온라인")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile27 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member17)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(23)
                                .maxAge(27)
                                .mbti(MBTI.INFP.name())
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(1)
                                .comment("심리학 연구 스터디 함께 하실 분 구합니다.")
                                .studyType(StudyType.STUDY)
                                .isSchool(false)
                                .majorName("심리학과")
                                .professorName("박교수님")
                                .isOnline(true)
                                .studyTimes(2)
                                .place("온라인")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile28 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member18)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(22)
                                .maxAge(27)
                                .mbti(MBTI.ESFJ.name())
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(1)
                                .comment("화학 실험 스터디원 모집합니다.")
                                .studyType(StudyType.MAJOR)
                                .isSchool(true)
                                .majorName("화학공학과")
                                .professorName("김교수님")
                                .isOnline(false)
                                .studyTimes(2)
                                .place("연구실")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile29 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member19)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(21)
                                .maxAge(26)
                                .mbti(MBTI.ISFJ.name())
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(2)
                                .comment("간호학과 학습 그룹 구합니다.")
                                .studyType(StudyType.STUDY)
                                .isSchool(true)
                                .majorName("간호학과")
                                .professorName("박교수님")
                                .isOnline(true)
                                .studyTimes(3)
                                .place("온라인")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile30 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member20)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(23)
                                .maxAge(28)
                                .mbti(MBTI.INTJ.name())
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(5)
                                .currentPeople(1)
                                .comment("환경공학 연구팀 모집합니다.")
                                .studyType(StudyType.MAJOR)
                                .isSchool(false)
                                .majorName("환경공학과")
                                .professorName("최교수님")
                                .isOnline(false)
                                .studyTimes(2)
                                .place("카페")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile31 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member21)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.JUNIOR)
                                .minAge(20)
                                .maxAge(25)
                                .mbti(MBTI.ENTP.name())
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(2)
                                .comment("건설공학 팀 프로젝트 모집합니다.")
                                .studyType(StudyType.STUDY)
                                .isSchool(true)
                                .majorName("건설방재공학과")
                                .professorName("이교수님")
                                .isOnline(true)
                                .studyTimes(3)
                                .place("온라인")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile32 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member22)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(22)
                                .maxAge(28)
                                .mbti(MBTI.ISFP.name())
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(1)
                                .comment("국어국문학 연구 모임 모집합니다.")
                                .studyType(StudyType.NON_MAJOR)
                                .isSchool(false)
                                .majorName("국어국문학과")
                                .professorName("정교수님")
                                .isOnline(false)
                                .studyTimes(2)
                                .place("도서관")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile33 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member23)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(21)
                                .maxAge(27)
                                .mbti(MBTI.ISTP.name())
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("수학 문제 풀이 스터디원 모집")
                                .studyType(StudyType.STUDY)
                                .isSchool(true)
                                .majorName("수학과")
                                .professorName("이교수님")
                                .isOnline(true)
                                .studyTimes(3)
                                .place("온라인")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile34 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member24)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(25)
                                .mbti(MBTI.ESFP.name())
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(5)
                                .currentPeople(2)
                                .comment("경제학과 세미나 참여")
                                .studyType(StudyType.MAJOR)
                                .isSchool(false)
                                .majorName("경제학과")
                                .professorName("박교수님")
                                .isOnline(false)
                                .studyTimes(2)
                                .place("카페")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile35 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member25)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(22)
                                .maxAge(27)
                                .mbti(MBTI.ENTP.name())
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("기계공학 전공 스터디 모집")
                                .studyType(StudyType.MAJOR)
                                .isSchool(true)
                                .majorName("기계공학과")
                                .professorName("윤교수님")
                                .isOnline(false)
                                .studyTimes(3)
                                .place("도서관")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile36 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member26)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(21)
                                .maxAge(26)
                                .mbti(MBTI.ISFP.name())
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("사회복지 세미나 그룹 모집")
                                .studyType(StudyType.NON_MAJOR)
                                .isSchool(false)
                                .majorName("사회복지학과")
                                .professorName("이교수님")
                                .isOnline(true)
                                .studyTimes(2)
                                .place("온라인")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile37 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member27)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(23)
                                .maxAge(28)
                                .mbti(MBTI.ISTJ.name())
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(5)
                                .currentPeople(2)
                                .comment("철학 토론 동아리 모집")
                                .studyType(StudyType.NON_MAJOR)
                                .isSchool(true)
                                .majorName("철학과")
                                .professorName("최교수님")
                                .isOnline(false)
                                .studyTimes(3)
                                .place("카페")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile38 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member28)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(22)
                                .maxAge(27)
                                .mbti(MBTI.INFJ.name())
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(1)
                                .comment("약학 실습 연구 스터디 모집")
                                .studyType(StudyType.MAJOR)
                                .isSchool(false)
                                .majorName("약학과")
                                .professorName("박교수님")
                                .isOnline(true)
                                .studyTimes(2)
                                .place("온라인")
                                .mateType(MateType.STUDY)
                                .build()
                );
                MemberSecondProfile secondProfile39 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member29)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(23)
                                .maxAge(27)
                                .mbti(MBTI.ENFP.name())
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("경영학 토론 스터디 함께해요!")
                                .studyType(StudyType.STUDY)
                                .isSchool(true)
                                .majorName("경영학과")
                                .professorName("김교수님")
                                .isOnline(true)
                                .studyTimes(2)
                                .place("온라인")
                                .mateType(MateType.STUDY)
                                .build()
                );

                MemberSecondProfile secondProfile40 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member30)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(21)
                                .maxAge(26)
                                .mbti(MBTI.INTP.name())
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(5)
                                .currentPeople(3)
                                .comment("웹 개발 프로젝트 팀원 모집 중!")
                                .studyType(StudyType.MAJOR)
                                .isSchool(false)
                                .majorName("컴퓨터공학과")
                                .professorName("이교수님")
                                .isOnline(false)
                                .studyTimes(3)
                                .place("스터디 카페")
                                .mateType(MateType.STUDY)
                                .build()
                );
                MemberSecondProfile secondProfile41 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member31)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("ENTP")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("헬스장에서 운동 함께할 파트너 찾습니다.")
                                .exerciseType(ExerciseType.FITNESS)
                                .isSchool(false)
                                .foodTypes(Set.of(FoodType.KOREAN))
                                .studyType(null)
                                .majorName(null)
                                .professorName(null)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("헬스장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile42 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member32)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(21)
                                .maxAge(26)
                                .mbti("ISFJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("요가 함께 배우실 분 모집합니다!")
                                .exerciseType(ExerciseType.RUNNING)
                                .isSchool(true)
                                .foodTypes(Set.of(FoodType.KOREAN))
                                .studyType(StudyType.NON_MAJOR)
                                .majorName("심리학과")
                                .professorName("박교수님")
                                .isOnline(false)
                                .studyTimes(3)
                                .place("요가 스튜디오")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile43 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member33)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.JUNIOR)
                                .minAge(20)
                                .maxAge(25)
                                .mbti("ISTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("테니스 함께 치실 분 구합니다!")
                                .exerciseType(ExerciseType.TABLE_TENNIS)
                                .isSchool(false)
                                .foodTypes(Set.of(FoodType.OTHER))
                                .studyType(StudyType.MAJOR)
                                .majorName("체육학과")
                                .professorName("최교수님")
                                .isOnline(false)
                                .studyTimes(2)
                                .place("테니스장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile44 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member34)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(22)
                                .maxAge(28)
                                .mbti("ENFP")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("배드민턴 같이 치실 분!")
                                .exerciseType(ExerciseType.TENNIS_BADMINTON)
                                .isSchool(true)
                                .foodTypes(Set.of(FoodType.JAPANESE))
                                .studyType(StudyType.MAJOR)
                                .majorName("스포츠과학과")
                                .professorName("이교수님")
                                .isOnline(false)
                                .studyTimes(3)
                                .place("체육관")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile45 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member35)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(20)
                                .maxAge(27)
                                .mbti("INFJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("아침 러닝 함께 하실 분 구합니다.")
                                .exerciseType(ExerciseType.RUNNING)
                                .isSchool(false)
                                .foodTypes(Set.of(FoodType.KOREAN))
                                .studyType(StudyType.NON_MAJOR)
                                .majorName("화학공학과")
                                .professorName("장교수님")
                                .isOnline(false)
                                .studyTimes(2)
                                .place("공원")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile46 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member36)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(21)
                                .maxAge(26)
                                .mbti("ESTJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(5)
                                .currentPeople(2)
                                .comment("수영장 같이 다니실 분!")
                                .exerciseType(ExerciseType.OTHER)
                                .isSchool(true)
                                .foodTypes(Set.of(FoodType.KOREAN))
                                .studyType(StudyType.MAJOR)
                                .majorName("환경공학과")
                                .professorName("윤교수님")
                                .isOnline(false)
                                .studyTimes(3)
                                .place("수영장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile47 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member37)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(22)
                                .maxAge(28)
                                .mbti("INTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("클라이밍 도전해볼 분 찾습니다!")
                                .exerciseType(ExerciseType.CLIMBING)
                                .isSchool(false)
                                .foodTypes(Set.of(FoodType.WESTERN))
                                .studyType(StudyType.NON_MAJOR)
                                .majorName("물리학과")
                                .professorName("한교수님")
                                .isOnline(false)
                                .studyTimes(2)
                                .place("클라이밍 센터")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile48 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member38)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.JUNIOR)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("ESFP")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("조깅 모임 참가하실 분!")
                                .exerciseType(ExerciseType.RUNNING)
                                .isSchool(true)
                                .foodTypes(Set.of(FoodType.JAPANESE))
                                .studyType(StudyType.MAJOR)
                                .majorName("간호학과")
                                .professorName("김교수님")
                                .isOnline(false)
                                .studyTimes(3)
                                .place("한강공원")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile49 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member39)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(21)
                                .maxAge(28)
                                .mbti("ISTJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("축구팀 멤버 모집 중!")
                                .exerciseType(ExerciseType.SOCCER)
                                .isSchool(false)
                                .foodTypes(Set.of(FoodType.KOREAN))
                                .studyType(StudyType.NON_MAJOR)
                                .majorName("경영정보학과")
                                .professorName("정교수님")
                                .isOnline(false)
                                .studyTimes(2)
                                .place("운동장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );
                MemberSecondProfile secondProfile50 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member40)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("ENFJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("주말마다 함께 사이클링 하실 분 모집합니다!")
                                .exerciseType(ExerciseType.OTHER)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("한강 자전거 도로")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile51 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member41)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(22)
                                .maxAge(28)
                                .mbti("INTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(1)
                                .comment("주중 저녁 러닝 메이트 찾습니다!")
                                .exerciseType(ExerciseType.RUNNING)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(3)
                                .place("서울숲")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile52 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member42)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(23)
                                .maxAge(27)
                                .mbti("ISFJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("요가나 필라테스 함께 다니실 분 구합니다!")
                                .exerciseType(ExerciseType.FITNESS)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("홍대 요가 스튜디오")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile53 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member43)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(25)
                                .mbti("ENTJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("헬스장에서 보디빌딩 함께 하실 분!")
                                .exerciseType(ExerciseType.FITNESS)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(4)
                                .place("강남 헬스클럽")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile54 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member44)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.JUNIOR)
                                .minAge(21)
                                .maxAge(26)
                                .mbti("INFJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(5)
                                .currentPeople(2)
                                .comment("주말마다 등산 가실 분 구해요!")
                                .exerciseType(ExerciseType.RUNNING)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("북한산 국립공원")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile55 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member45)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(20)
                                .maxAge(23)
                                .mbti("ESFP")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(6)
                                .currentPeople(3)
                                .comment("주말 풋살 팀원 모집 중입니다!")
                                .exerciseType(ExerciseType.SOCCER)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("한강 풋살 경기장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile56 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member46)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(22)
                                .maxAge(27)
                                .mbti("ENTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("클라이밍 함께 배우실 분 구해요!")
                                .exerciseType(ExerciseType.CLIMBING)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(3)
                                .place("홍대 클라이밍 센터")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile57 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member47)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(21)
                                .maxAge(23)
                                .mbti("ISTJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("저녁마다 같이 수영하실 분 모집합니다.")
                                .exerciseType(ExerciseType.OTHER)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("신촌 수영장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile58 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member48)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(23)
                                .maxAge(25)
                                .mbti("INTJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("함께 주짓수 배우실 분 찾아요!")
                                .exerciseType(ExerciseType.OTHER)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("강남 주짓수 도장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile59 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member49)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(22)
                                .maxAge(27)
                                .mbti("ESTJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("필라테스 같이 배우실 분을 찾습니다!")
                                .exerciseType(ExerciseType.FITNESS)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("건대 필라테스 센터")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile60 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member50)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(21)
                                .maxAge(27)
                                .mbti("ENFP")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(1)
                                .comment("주말 농구팀 함께 하실 분 모집!")
                                .exerciseType(ExerciseType.BASKETBALL)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(3)
                                .place("서울 체육관")
                                .mateType(MateType.EXERCISE)
                                .build()
                );
                // SecondProfile 61~70 추가
                MemberSecondProfile secondProfile61 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member51)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(22)
                                .maxAge(23)
                                .mbti("INTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("매주 토요일 축구 모임 함께 하실 분!")
                                .exerciseType(ExerciseType.SOCCER)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("학교 운동장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile62 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member52)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(21)
                                .maxAge(26)
                                .mbti("ENTP")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("필라테스 함께 다닐 친구 구해요!")
                                .exerciseType(ExerciseType.OTHER)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(3)
                                .place("홍대 필라테스 스튜디오")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile63 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member53)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(23)
                                .maxAge(25)
                                .mbti("INFJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("등산 같이 다닐 분 구합니다!")
                                .exerciseType(ExerciseType.RUNNING)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("북한산 국립공원")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile64 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member54)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(25)
                                .mbti("ISFP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(5)
                                .currentPeople(2)
                                .comment("매주 아침 러닝 함께 하실 분!")
                                .exerciseType(ExerciseType.RUNNING)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("한강 공원")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile65 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member55)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(22)
                                .maxAge(26)
                                .mbti("ESTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(1)
                                .comment("농구 같이 할 팀원 모집 중!")
                                .exerciseType(ExerciseType.BASKETBALL)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("체육관 농구장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile66 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member56)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.SENIOR)
                                .minAge(23)
                                .maxAge(27)
                                .mbti("ENFP")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(2)
                                .comment("요가 함께 다닐 분 구해요!")
                                .exerciseType(ExerciseType.FITNESS)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("이태원 요가 스튜디오")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile67 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member57)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(24)
                                .maxAge(26)
                                .mbti("ISTJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("클라이밍 배울 사람 구해요!")
                                .exerciseType(ExerciseType.CLIMBING)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("클라이밍 짐")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile68 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member58)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(21)
                                .maxAge(26)
                                .mbti("ESFP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("주말마다 춤 연습하실 분 모집!")
                                .exerciseType(ExerciseType.OTHER)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("홍대 댄스 스튜디오")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile69 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member59)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(22)
                                .maxAge(27)
                                .mbti("INFJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("주말마다 수영 같이 하실 분 구해요!")
                                .exerciseType(ExerciseType.OTHER)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(2)
                                .place("학교 수영장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );

                MemberSecondProfile secondProfile70 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member60)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(24)
                                .maxAge(27)
                                .mbti("ENTJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(5)
                                .currentPeople(3)
                                .comment("매주 주말 헬스 같이 하실 분!")
                                .exerciseType(ExerciseType.FITNESS)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(3)
                                .place("학교 헬스장")
                                .mateType(MateType.EXERCISE)
                                .build()
                );
                MemberSecondProfile secondProfile71 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member61)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("INTJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("브런치 카페 탐방 같이 하실 분 구합니다!")
                                .mateType(MateType.MEAL)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("홍대 카페거리")
                                .build()
                );

                MemberSecondProfile secondProfile72 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member62)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(21)
                                .maxAge(25)
                                .mbti("ISFP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("한식 좋아하는 분들과 같이 식사하고 싶어요!")
                                .mateType(MateType.MEAL)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("학교 근처 한식당")
                                .build()
                );

                MemberSecondProfile secondProfile73 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member63)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(25)
                                .mbti("ENTP")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("파스타나 스테이크 좋아하는 분들 모여요!")
                                .mateType(MateType.MEAL)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("강남 이탈리안 레스토랑")
                                .build()
                );

                MemberSecondProfile secondProfile74 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member64)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(22)
                                .maxAge(26)
                                .mbti("INFJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("맛있는 디저트 먹으면서 수다 떨고 싶어요!")
                                .mateType(MateType.MEAL)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("홍대 디저트 카페")
                                .build()
                );

                MemberSecondProfile secondProfile75 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member65)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("ISTJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("일식 덕후들 모여라!")
                                .mateType(MateType.MEAL)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("신촌 일식당")
                                .build()
                );

                MemberSecondProfile secondProfile76 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member66)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(21)
                                .maxAge(24)
                                .mbti("ESFP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("야식 같이 먹으러 가실 분?")
                                .mateType(MateType.MEAL)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("홍대 야식 골목")
                                .build()
                );

                MemberSecondProfile secondProfile77 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member67)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(20)
                                .maxAge(25)
                                .mbti("ENFP")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("고기 좋아하는 사람들 모여!")
                                .mateType(MateType.MEAL)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("을지로 고깃집")
                                .build()
                );

                MemberSecondProfile secondProfile78 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member68)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(22)
                                .maxAge(25)
                                .mbti("INTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("비건 음식 같이 먹어봐요!")
                                .mateType(MateType.MEAL)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("비건 레스토랑")
                                .build()
                );

                MemberSecondProfile secondProfile79 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member69)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(20)
                                .maxAge(24)
                                .mbti("ESTJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("라멘 투어 같이 하실 분?")
                                .mateType(MateType.MEAL)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("서울 라멘 투어")
                                .build()
                );

                MemberSecondProfile secondProfile80 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member70)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(21)
                                .maxAge(26)
                                .mbti("ENTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("디저트 카페 탐방 같이 해요!")
                                .mateType(MateType.MEAL)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("서울 디저트 카페")
                                .build()
                );
                MemberSecondProfile secondProfile81 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member71)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("INFJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("혼밥보단 같이 먹는 게 좋아요!")
                                .mateType(MateType.MEAL)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("학교 근처 한식당")
                                .build()
                );

                MemberSecondProfile secondProfile82 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member72)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(21)
                                .maxAge(25)
                                .mbti("ISFP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("맛집 찾아다니는 거 좋아해요!")
                                .mateType(MateType.MEAL)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("강남 맛집")
                                .build()
                );

                MemberSecondProfile secondProfile83 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member73)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(22)
                                .maxAge(25)
                                .mbti("ENTJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(4)
                                .currentPeople(2)
                                .comment("햄버거 덕후 모여요!")
                                .mateType(MateType.MEAL)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("홍대 햄버거 맛집")
                                .build()
                );

                MemberSecondProfile secondProfile84 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member74)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("ISTJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(3)
                                .currentPeople(1)
                                .comment("일식 좋아하는 분들 모집해요!")
                                .mateType(MateType.MEAL)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("이태원 일식당")
                                .build()
                );

                MemberSecondProfile secondProfile85 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member75)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(21)
                                .maxAge(26)
                                .mbti("ESFP")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(2)
                                .currentPeople(1)
                                .comment("혼밥보단 같이 먹는 게 좋아요!")
                                .mateType(MateType.MEAL)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(0)
                                .place("학교 근처 카페")
                                .build()
                );
                MemberSecondProfile secondProfile86 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member76)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("INFJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("혼밥이 심심해서 같이 먹을 친구 찾습니다!")
                                .exerciseType(null)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("학교 식당")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile87 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member77)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(21)
                                .maxAge(25)
                                .mbti("ENTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("점심에 같이 밥 먹을 사람 찾아요~!")
                                .exerciseType(null)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("학교 앞 김밥천국")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile88 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member78)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(19)
                                .maxAge(24)
                                .mbti("ISFP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("자취생 혼밥 너무 외로워요.. 같이 드실 분!")
                                .exerciseType(null)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("자취방 근처 카페")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile89 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member79)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(22)
                                .maxAge(26)
                                .mbti("ISTJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("저녁에 같이 밥 먹고 싶어요~")
                                .exerciseType(null)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("학교 기숙사 식당")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile90 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member80)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(25)
                                .mbti("ENFP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("밥 혼자 먹기 싫어요 ㅠ 같이 먹어요!")
                                .exerciseType(null)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("근처 맛집")
                                .mateType(MateType.MEAL)
                                .build()
                );

// 91~100도 같은 방식으로 추가할게! 🚀

                MemberSecondProfile secondProfile91 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member81)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(21)
                                .maxAge(26)
                                .mbti("ISTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("주말에 같이 브런치 먹을 사람!")
                                .exerciseType(null)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("홍대 브런치 카페")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile92 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member82)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(24)
                                .mbti("ESFJ")
                                .isHobbySame(true)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("혼밥이 너무 외로워서 같이 드실 분 구해요 ㅠㅠ")
                                .exerciseType(null)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("학교 카페")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile93 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member83)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(22)
                                .maxAge(26)
                                .mbti("INTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("저녁에 같이 파스타 먹으러 가실 분?")
                                .exerciseType(null)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("이태원 레스토랑")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile94 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member84)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(19)
                                .maxAge(23)
                                .mbti("ISFJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("혼밥러인데 같이 먹을 사람 구해요!")
                                .exerciseType(null)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("학교 구내식당")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile95 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member85)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(22)
                                .maxAge(27)
                                .mbti("ESTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("점심시간에 같이 먹을 밥친구 찾습니다!")
                                .exerciseType(null)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("회사 근처 식당")
                                .mateType(MateType.MEAL)
                                .build()
                );
                MemberSecondProfile secondProfile96 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member86)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(20)
                                .maxAge(25)
                                .mbti("INFJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("혼밥도 좋지만 같이 먹으면 더 좋아요!")
                                .exerciseType(null)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("학교 카페")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile97 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member87)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(21)
                                .maxAge(26)
                                .mbti("ENTP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("저녁에 같이 라멘 먹으러 가실 분?")
                                .exerciseType(null)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("홍대 라멘 맛집")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile98 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member88)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.PEER)
                                .minAge(19)
                                .maxAge(23)
                                .mbti("ISFP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("혼밥도 좋지만 같이 먹을 친구를 찾아요!")
                                .exerciseType(null)
                                .isSchool(true)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("학교 구내식당")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile99 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member89)
                                .gender(Gender.MALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(22)
                                .maxAge(27)
                                .mbti("ISTJ")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("점심시간에 같이 밥 먹을 사람!")
                                .exerciseType(null)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("회사 근처 식당")
                                .mateType(MateType.MEAL)
                                .build()
                );

                MemberSecondProfile secondProfile100 = memberSecondProfileRepository.save(
                        MemberSecondProfile.builder()
                                .member(member90)
                                .gender(Gender.FEMALE)
                                .memberSecondProfileMajorList(new ArrayList<>())
                                .studentNumber(StudentNumber.ALL)
                                .minAge(20)
                                .maxAge(26)
                                .mbti("ENFP")
                                .isHobbySame(false)
                                .memberSecondProfileTimes(new ArrayList<>())
                                .maxPeople(1)
                                .currentPeople(1)
                                .comment("같이 브런치 먹으러 가실 분?")
                                .exerciseType(null)
                                .isSchool(false)
                                .isOnline(false)
                                .studyTimes(1)
                                .place("강남 브런치 카페")
                                .mateType(MateType.MEAL)
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
                memberSecondProfileRepository.save(secondProfile10);
                memberSecondProfileRepository.save(secondProfile21);
                memberSecondProfileRepository.save(secondProfile22);
                memberSecondProfileRepository.save(secondProfile23);
                memberSecondProfileRepository.save(secondProfile24);
                memberSecondProfileRepository.save(secondProfile25);
                memberSecondProfileRepository.save(secondProfile26);
                memberSecondProfileRepository.save(secondProfile27);
                memberSecondProfileRepository.save(secondProfile28);
                memberSecondProfileRepository.save(secondProfile29);
                memberSecondProfileRepository.save(secondProfile30);
                memberSecondProfileRepository.save(secondProfile31);
                memberSecondProfileRepository.save(secondProfile32);
                memberSecondProfileRepository.save(secondProfile33);
                memberSecondProfileRepository.save(secondProfile34);
                memberSecondProfileRepository.save(secondProfile35);
                memberSecondProfileRepository.save(secondProfile36);
                memberSecondProfileRepository.save(secondProfile37);
                memberSecondProfileRepository.save(secondProfile38);
                memberSecondProfileRepository.save(secondProfile39);
                memberSecondProfileRepository.save(secondProfile40);
                memberSecondProfileRepository.save(secondProfile41);
                memberSecondProfileRepository.save(secondProfile42);
                memberSecondProfileRepository.save(secondProfile43);
                memberSecondProfileRepository.save(secondProfile44);
                memberSecondProfileRepository.save(secondProfile45);
                memberSecondProfileRepository.save(secondProfile46);
                memberSecondProfileRepository.save(secondProfile47);
                memberSecondProfileRepository.save(secondProfile48);
                memberSecondProfileRepository.save(secondProfile49);
                memberSecondProfileRepository.save(secondProfile50);
                memberSecondProfileRepository.save(secondProfile51);
                memberSecondProfileRepository.save(secondProfile52);
                memberSecondProfileRepository.save(secondProfile53);
                memberSecondProfileRepository.save(secondProfile54);
                memberSecondProfileRepository.save(secondProfile55);
                memberSecondProfileRepository.save(secondProfile56);
                memberSecondProfileRepository.save(secondProfile57);
                memberSecondProfileRepository.save(secondProfile58);
                memberSecondProfileRepository.save(secondProfile59);
                memberSecondProfileRepository.save(secondProfile60);
                memberSecondProfileRepository.save(secondProfile61);
                memberSecondProfileRepository.save(secondProfile62);
                memberSecondProfileRepository.save(secondProfile63);
                memberSecondProfileRepository.save(secondProfile64);
                memberSecondProfileRepository.save(secondProfile65);
                memberSecondProfileRepository.save(secondProfile66);
                memberSecondProfileRepository.save(secondProfile67);
                memberSecondProfileRepository.save(secondProfile68);
                memberSecondProfileRepository.save(secondProfile69);
                memberSecondProfileRepository.save(secondProfile70);
                memberSecondProfileRepository.save(secondProfile71);
                memberSecondProfileRepository.save(secondProfile72);
                memberSecondProfileRepository.save(secondProfile73);
                memberSecondProfileRepository.save(secondProfile74);
                memberSecondProfileRepository.save(secondProfile75);
                memberSecondProfileRepository.save(secondProfile76);
                memberSecondProfileRepository.save(secondProfile77);
                memberSecondProfileRepository.save(secondProfile78);
                memberSecondProfileRepository.save(secondProfile79);
                memberSecondProfileRepository.save(secondProfile80);
                memberSecondProfileRepository.save(secondProfile81);
                memberSecondProfileRepository.save(secondProfile82);
                memberSecondProfileRepository.save(secondProfile83);
                memberSecondProfileRepository.save(secondProfile84);
                memberSecondProfileRepository.save(secondProfile85);
                memberSecondProfileRepository.save(secondProfile86);
                memberSecondProfileRepository.save(secondProfile87);
                memberSecondProfileRepository.save(secondProfile88);
                memberSecondProfileRepository.save(secondProfile89);
                memberSecondProfileRepository.save(secondProfile90);
                memberSecondProfileRepository.save(secondProfile91);
                memberSecondProfileRepository.save(secondProfile92);
                memberSecondProfileRepository.save(secondProfile93);
                memberSecondProfileRepository.save(secondProfile94);
                memberSecondProfileRepository.save(secondProfile95);
                memberSecondProfileRepository.save(secondProfile96);
                memberSecondProfileRepository.save(secondProfile97);
                memberSecondProfileRepository.save(secondProfile98);
                memberSecondProfileRepository.save(secondProfile99);
                memberSecondProfileRepository.save(secondProfile100);

                MemberSecondProfileTimes memberSecondProfileTimes = MemberSecondProfileTimes.builder()
                        .week(Week.MON)
                        .times(Set.of(1,2,3))
                        .memberSecondProfile(secondProfile11)
                        .build();

                memberSecondProfileTimesRepository.save(memberSecondProfileTimes);

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

