package com.umc.meetpick.service.matching;


import com.umc.meetpick.dto.*;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.enums.*;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import com.umc.meetpick.repository.member.MemberLikesRepository;
import com.umc.meetpick.repository.member.MemberProfileRepository;
import com.umc.meetpick.service.home.factory.MemberQueryStrategyFactory;
import com.umc.meetpick.service.home.strategy.MemberQueryStrategy;
import com.umc.meetpick.service.matching.factory.AlarmQueryStrategyFactory;
import com.umc.meetpick.service.matching.factory.MatchQueryStrategyFactory;
import com.umc.meetpick.service.matching.strategy.AlarmQueryStrategy;
import com.umc.meetpick.service.matching.strategy.MatchQueryStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;



import static com.umc.meetpick.common.util.DateTimeUtil.getTime;
import static com.umc.meetpick.service.matching.factory.MatchingDtoFactory.memberSecondProfileToAlarmDtoList;
import static com.umc.meetpick.service.matching.factory.MatchingDtoFactory.memberSecondProfileToMatchPageDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchingServiceImpl implements MatchingService {

    //TODO Request SERVICE랑 합치기

    private final MemberSecondProfileRepository memberSecondProfileRepository;
    private final MemberRepository memberRepository;
    private final MemberMappingRepository memberMappingRepository;
    private final MemberLikesRepository memberLikesRepository;// 좋아요 여부 확인용
    private final MemberProfileRepository memberProfileRepository;// 프로필 정보 조회용
    private final int minCondition = 3;
    private int page = 0;
    private final int pageSize = 100;
    private Pageable pageable = PageRequest.of(page, pageSize);

    @Override
    public Object match(Long memberId, String mateType) {

        MateType type = MateType.fromString(mateType);

        switch (type) {
            case MEAL -> {
                List<RecommendDto.FoodRecommendDto> foodRecommendDtos = new ArrayList<>();

                foodRecommendDtos.add(RecommendDto.FoodRecommendDto.builder()
                        .memberSecondProfileId(1L)
                        .nickName("예시1")
                        .studentNumber("100학번")
                        .foodTypes(Set.of("양식", "중식", "어쩌구"))
                        .gender("남성")
                        .imageUrl("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/default.png")
                        .mbti(MBTI.ENFJ)
                        .build());

                foodRecommendDtos.add(RecommendDto.FoodRecommendDto.builder()
                        .memberSecondProfileId(2L)
                        .nickName("예시2")
                        .studentNumber("101학번")
                        .foodTypes(Set.of("한식", "일식"))
                        .gender("여성")
                        .imageUrl("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/default.png")
                        .mbti(MBTI.INFJ)
                        .build());

                foodRecommendDtos.add(RecommendDto.FoodRecommendDto.builder()
                        .memberSecondProfileId(3L)
                        .nickName("예시3")
                        .studentNumber("102학번")
                        .foodTypes(Set.of("분식", "디저트"))
                        .gender("남성")
                        .imageUrl("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/default.png")
                        .mbti(MBTI.ESFP)
                        .build());

                return RecommendDto.FoodRecommendPageDto.builder()
                        .foodRecommendDtos(foodRecommendDtos)
                        .currentPage(0)
                        .hasNextPage(false)
                        .build();
            }
            case EXERCISE -> {
                List<RecommendDto.ExerciseRecommendDto> exerciseRecommendDtos = new ArrayList<>();

                exerciseRecommendDtos.add(RecommendDto.ExerciseRecommendDto.builder()
                        .memberSecondProfileId(4L)
                        .nickName("운동예시1")
                        .studentNumber("103학번")
                        .gender("남성")
                        .mbti(MBTI.ISTJ)
                        .imageUrl("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/default.png")
                        .exerciseType("헬스")
                        .build());

                exerciseRecommendDtos.add(RecommendDto.ExerciseRecommendDto.builder()
                        .memberSecondProfileId(5L)
                        .nickName("운동예시2")
                        .studentNumber("104학번")
                        .gender("여성")
                        .mbti(MBTI.INFP)
                        .imageUrl("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/default.png")
                        .exerciseType("요가")
                        .build());

                exerciseRecommendDtos.add(RecommendDto.ExerciseRecommendDto.builder()
                        .memberSecondProfileId(6L)
                        .nickName("운동예시3")
                        .studentNumber("105학번")
                        .gender("남성")
                        .mbti(MBTI.ESTP)
                        .imageUrl("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/default.png")
                        .exerciseType("조깅")
                        .build());

                return RecommendDto.ExerciseRecommendPageDto.builder()
                        .exerciseRecommendDtos(exerciseRecommendDtos)
                        .currentPage(0)
                        .hasNextPage(false)
                        .build();
            }
            case STUDY -> {
                List<RecommendDto.StudyRecommendDto> studyRecommendDtos = new ArrayList<>();

                studyRecommendDtos.add(RecommendDto.StudyRecommendDto.builder()
                        .memberSecondProfileId(7L)
                        .nickName("공부예시1")
                        .studentNumber("106학번")
                        .gender("여성")
                        .mbti(MBTI.INTP)
                        .studyType("수학")
                        .imageUrl("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/default.png")
                        .build());

                studyRecommendDtos.add(RecommendDto.StudyRecommendDto.builder()
                        .memberSecondProfileId(8L)
                        .nickName("공부예시2")
                        .studentNumber("107학번")
                        .gender("남성")
                        .mbti(MBTI.ENTJ)
                        .studyType("영어")
                        .imageUrl("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/default.png")
                        .build());

                studyRecommendDtos.add(RecommendDto.StudyRecommendDto.builder()
                        .memberSecondProfileId(9L)
                        .nickName("공부예시3")
                        .studentNumber("108학번")
                        .gender("여성")
                        .mbti(MBTI.ISFJ)
                        .studyType("교양")
                        .imageUrl("https://hangeulbucket.s3.ap-northeast-2.amazonaws.com/default.png")
                        .build());

                return RecommendDto.StudyRecommendPageDto.builder()
                        .studyRecommendDtos(studyRecommendDtos)
                        .currentPage(0)
                        .hasNextPage(false)
                        .build();
            }
        }
        return null;
    }


    @Override
    public MatchPageDto getMatchRequests(Long memberId, String mateType, Pageable pageable) {

        MateType type = MateType.fromString(mateType);
        Member member = memberRepository.findMemberById(memberId);

        MatchQueryStrategyFactory factory = new MatchQueryStrategyFactory(memberMappingRepository);
        MatchQueryStrategy strategy = factory.getStrategy(type);
        Page<MemberSecondProfileMapping> memberProfile = strategy.getMemberProfiles(member, type, pageable, false);

        // 3. 최종 응답 DTO 생성
        return memberSecondProfileToMatchPageDto(memberProfile);
    }

    private List<MemberSecondProfile> getMatchingType(MateType mateType){
        return memberSecondProfileRepository.findMemberSecondProfilesByMateType(mateType, pageable).getContent();
    }

    // TODO 디자인 패턴 적용 및 내용 수정
    @Override
    public AlarmDto.AlarmPageResponseDto getAlarms(String mateType, Pageable pageable, Long memberId) {

        MateType type = MateType.fromString(mateType);
        Member member = memberRepository.findMemberById(memberId);

        AlarmQueryStrategyFactory factory = new AlarmQueryStrategyFactory(memberMappingRepository);
        AlarmQueryStrategy strategy = factory.getStrategy(type);
        Page<MemberSecondProfileMapping> memberProfile = strategy.getSecondProfilesByMateType(member, type, pageable, false);

        return memberSecondProfileToAlarmDtoList(memberProfile);
    }

    @Override
    public MatchPageDto getCompletedMatches(Long memberId, String mateType, Pageable pageable) {

        MateType type = MateType.fromString(mateType);
        Member member = memberRepository.findMemberById(memberId);

        MatchQueryStrategyFactory factory = new MatchQueryStrategyFactory(memberMappingRepository);
        MatchQueryStrategy strategy = factory.getStrategy(type);
        Page<MemberSecondProfileMapping> memberProfile = strategy.getMemberProfiles(member, type, pageable, true);

        // 3. 최종 응답 DTO 생성
        return memberSecondProfileToMatchPageDto(memberProfile);
    }

    private MatchResponseDto requestToMatchResponseDto(Member member, MemberSecondProfile memberSecondProfile){

        MemberProfile memberProfile = member.getMemberProfile();

        // TODO MateType에 따라서 다른 로직 구성하기
        return MatchResponseDto.builder()
                .memberId(member.getId())
                .foodType(memberSecondProfile.getFoodTypes().stream()
                        .map(FoodType::getKoreanName)
                        .collect(Collectors.toSet()))
                .hobby(memberProfile.getHobbies().stream()
                        .map(Hobby::getKoreanName)
                        .collect(Collectors.toSet()))
                .requestId(memberSecondProfile.getId())
                .gender(memberSecondProfile.getGender().getKoreanName())
                .build();
    }



        @Override
        public ProfileDetailListResponseDto getAllProfiles(Long memberId, MateType mateType, FilterRequestDTO filterRequest, Pageable pageable) {
            Specification<MemberSecondProfile> spec = (root, query, builder) -> {
                List<Predicate> predicates = new ArrayList<>();
// 필터 적용 전 로그
                log.info("=== 필터 조건 ===");
                log.info("mateType: {}", mateType);
                log.info("studyType: {}", filterRequest.getStudyType());
                log.info("gender: {}", filterRequest.getGender());
                log.info("studentNumber: {}", filterRequest.getStudentNumber());
                log.info("minAge: {}", filterRequest.getMinAge());
                log.info("maxAge: {}", filterRequest.getMaxAge());
                log.info("=============");

                // 기본 필터: mateType
                predicates.add(builder.equal(root.get("mateType"), mateType));

                // 공통 필터
                // 1. 성별 필터
                if (filterRequest.getGender() != null) {
                    predicates.add(builder.equal(root.get("gender"), filterRequest.getGender()));
                }

                // 2. 학번 필터
                if (filterRequest.getStudentNumber() != null) {
                    predicates.add(builder.equal(root.get("studentNumber"), filterRequest.getStudentNumber()));
                }

                // 3. 나이 필터
                if (filterRequest.getMinAge() != null) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get("minAge"), filterRequest.getMinAge()));
                }
                if (filterRequest.getMaxAge() != null) {
                    predicates.add(builder.lessThanOrEqualTo(root.get("maxAge"), filterRequest.getMaxAge()));
                }

                // 4. 요일/시간 필터
                if (filterRequest.getAvailableDays() != null && !filterRequest.getAvailableDays().isEmpty()) {
                    predicates.add(root.join("memberSecondProfileTimes").get("week").in(filterRequest.getAvailableDays()));
                }
                if (filterRequest.getAvailableTimes() != null && !filterRequest.getAvailableTimes().isEmpty()) {
                    predicates.add(root.join("memberSecondProfileTimes").get("times").in(filterRequest.getAvailableTimes()));
                }

                // MateType별 특수 필터
                switch (mateType) {
    //                case STUDY:
    //                    if (filterRequest.getSubjectType() != null) {
    //                        predicates.add(builder.equal(root.get("subjectType"), filterRequest.getSubjectType()));
    //                    }
    //                    if (filterRequest.getSubjectType() == SubjectType.CERTIFICATE
    //                            && filterRequest.getCertificateType() != null) {
    //                        predicates.add(builder.equal(root.get("certificateType"), filterRequest.getCertificateType()));
    //                    }
    //                    break;
                    case STUDY: //Certificate 사용하지 않는 코드
                        if (filterRequest.getStudyType() != null) {   // subjectType -> studyType
                            predicates.add(builder.equal(root.get("studyType"), filterRequest.getStudyType()));
                        }
                        break;

                    case EXERCISE:
                        if (filterRequest.getExerciseTypes() != null && !filterRequest.getExerciseTypes().isEmpty()) {
                            predicates.add(root.get("exerciseTypes").in(filterRequest.getExerciseTypes()));
                        }
                        break;

                    case MEAL:
                        if (filterRequest.getFoodTypes() != null && !filterRequest.getFoodTypes().isEmpty()) {
                            predicates.add(root.get("foodTypes").in(filterRequest.getFoodTypes()));
                        }
                        break;
                }

                // 최신순 정렬 추가
                query.orderBy(builder.desc(root.get("createdAt")));

                return builder.and(predicates.toArray(new Predicate[0]));
            };

            // 필터링된 데이터 조회
            Page<MemberSecondProfile> profiles = memberSecondProfileRepository.findAll(spec, pageable);


            log.info("=== 조회된 프로필 데이터 ===");
            profiles.getContent().forEach(profile -> {
                log.info("Profile ID: {}", profile.getId());
                log.info("MateType: {}", profile.getMateType());
                log.info("StudyType: {}", profile.getStudyType());
                log.info("Gender: {}", profile.getGender());
                log.info("StudentNumber: {}", profile.getStudentNumber());
                log.info("========================");
            });


            // DTO 변환
            List<ProfileDetailResponseDto> profileDtos = profiles.getContent().stream()
                    .map(secondProfile -> {
                        Member profileMember = secondProfile.getMember();
                        boolean isLiked = memberLikesRepository
                                .existsByMemberAndMemberSecondProfile(profileMember, secondProfile);
                        return ProfileDetailResponseDto.from(profileMember, secondProfile, isLiked);
                    })
                    .collect(Collectors.toList());


            // DTO 변환 후에 로그 추가
            log.info("=== 응답 데이터 ===");
            profileDtos.forEach(dto -> {
                log.info("Nickname: {}", dto.getNickname());
                log.info("MateType: {}", dto.getPreferenceInfo().getMateType());
                // MateType별 조건부 로그
                if (dto.getPreferenceInfo().getMateType() == MateType.MEAL) {
                    log.info("FoodTypes: {}", dto.getPreferenceInfo().getFoodTypes());
                } else if (dto.getPreferenceInfo().getMateType() == MateType.STUDY) {
                    log.info("StudyType: {}", dto.getPreferenceInfo().getStudyType());
                } else if (dto.getPreferenceInfo().getMateType() == MateType.EXERCISE) {
                    log.info("ExerciseType: {}", dto.getPreferenceInfo().getExerciseType());
                }
                log.info("------------------------");
            });

            return ProfileDetailListResponseDto.from(
                    profileDtos,
                    profiles.getTotalPages(),
                    profiles.getTotalElements(),
                    profiles.hasNext()
            );


        }

}
