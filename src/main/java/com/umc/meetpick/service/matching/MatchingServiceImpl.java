package com.umc.meetpick.service.matching;


import com.umc.meetpick.dto.*;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.Hobby;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import com.umc.meetpick.service.home.factory.MemberQueryStrategyFactory;
import com.umc.meetpick.service.home.strategy.MemberQueryStrategy;
import com.umc.meetpick.service.matching.factory.AlarmQueryStrategyFactory;
import com.umc.meetpick.service.matching.factory.MatchQueryStrategyFactory;
import com.umc.meetpick.service.matching.strategy.AlarmQueryStrategy;
import com.umc.meetpick.service.matching.strategy.MatchQueryStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.umc.meetpick.common.util.DateTimeUtil.getTime;
import static com.umc.meetpick.service.matching.factory.MatchingDtoFactory.memberSecondProfileToAlarmDtoList;
import static com.umc.meetpick.service.matching.factory.MatchingDtoFactory.memberSecondProfileToMatchPageDto;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

    //TODO Request SERVICE랑 합치기

    private final MemberSecondProfileRepository memberSecondProfileRepository;
    private final MemberRepository memberRepository;
    private final MemberMappingRepository memberMappingRepository;
    private final int minCondition = 3;
    private int page = 0;
    private final int pageSize = 100;
    private Pageable pageable = PageRequest.of(page, pageSize);

    @Override
    public List<MatchResponseDto> match(Long memberId, MateType mateType){

        Member member = memberRepository.findMemberById(memberId);

        List<MatchResponseDto> matchResponseDtoList = new ArrayList<>();

        int recommendationNumber = 5;

        while(page < 5) {

            List<MemberSecondProfile> requestList = getMatchingType(mateType);

            //TODO 추천 로직 변경하기
            requestList.forEach(memberSecondProfile -> {

                int conditionMatching = 0;

                // 나이 조건 체크
                if((memberSecondProfile.getMinAge() <= member.getAge() && memberSecondProfile.getMaxAge() >= member.getAge()) || memberSecondProfile.getMaxAge() == null){
                    conditionMatching++;
                }

                // 성별 조건 체크
                if(memberSecondProfile.getGender() == member.getGender() || memberSecondProfile.getGender() == null){
                    conditionMatching++;
                }

                // MBTI 조건 체크
                if(memberSecondProfile.getMbti() == null || memberSecondProfile.getMbti().contains(member.getMemberProfile().getMBTI().name())){
                    conditionMatching++;
                }

                // 조건이 충족되면 matchResponseDtoList에 추가
                if(conditionMatching >= minCondition){
                    matchResponseDtoList.add(requestToMatchResponseDto(member, memberSecondProfile));
                }
            });

            // 페이지를 넘어가면 다음 페이지로 이동
            if (matchResponseDtoList.size() < recommendationNumber) {
                page++;
                pageable = PageRequest.of(page, pageSize);
            }
        }

        return matchResponseDtoList;
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
        Page<MemberSecondProfileMapping> memberProfile = strategy.getSecondProfilesByMateType(member, type, pageable);

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
}
