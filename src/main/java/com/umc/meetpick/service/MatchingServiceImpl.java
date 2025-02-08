package com.umc.meetpick.service;


import com.umc.meetpick.dto.AlarmResponseDto;
import com.umc.meetpick.dto.MatchRequestDto;
import com.umc.meetpick.dto.MatchRequestListDto;
import com.umc.meetpick.dto.MatchResponseDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                if(memberSecondProfile.getMbti() == null || memberSecondProfile.getMbti().contains(member.getMemberProfile().getMBTI())){
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
    public MatchRequestListDto getMatchRequests(Long memberId, Pageable pageable) {

        // 1. 페이징된 Request 엔티티 조회
        // TODO 만약 요청이 하나도 없는 경우 예외 처리하기
        Page<MemberSecondProfile> requests = memberSecondProfileRepository.findMemberSecondProfileByMemberId(memberId, pageable);

        // 2. Request 엔티티를 DTO로 변환
        List<MatchRequestDto> matchRequests = requests.getContent().stream()
                .map(MatchRequestDto::from)
                .collect(Collectors.toList());

        // 3. 최종 응답 DTO 생성
        return MatchRequestListDto.builder()
                .requests(matchRequests)
                .totalPages(requests.getTotalPages())
                .totalElements(requests.getTotalElements())
                .hasNext(requests.hasNext())
                .build();
    }

    private List<MemberSecondProfile> getMatchingType(MateType mateType){
        return memberSecondProfileRepository.findMemberSecondProfilesByMateType(mateType, pageable).getContent();
    }

    // TODO 디자인 패턴 적용 및 내용 수정
    @Override
    public List<AlarmResponseDto> getAlarms(Long memberId, MateType mateType) {

        Member member = memberRepository.findMemberById(memberId);

        // TODO 인덱싱 추가하기 & 예외처리 & pageable 처리 다르게 하기 (무한스크롤)
        List<MemberSecondProfileMapping> memberSecondProfileMappings = memberMappingRepository.findAllByMemberSecondProfile_MemberOrderByCreatedAt(member, pageable).getContent();

        return memberSecondProfileMappings.stream()
                .map(mapping -> {
                    MemberSecondProfile memberSecondProfile = mapping.getMemberSecondProfile();
                    return AlarmResponseDto.builder()
                            .mateType(memberSecondProfile.getMateType().getKoreanName())
                            .content("새로운 알림을 확인해보세요!")
                            .createdAt(getTime(mapping.getCreatedAt()))
                            .mappingId(mapping.getId())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MatchRequestDto> getCompletedMatches(Long memberId, MateType mateType) {
        Member member = memberRepository.findMemberById(memberId);

        //TODO 완성된거만 뽑도록 바꾸기

        return memberMappingRepository.findAllByMemberSecondProfile_MemberOrderByCreatedAt(member, pageable)
                .getContent()
                .stream()
                .map(mapping -> {
                    MemberSecondProfile memberSecondProfile = mapping.getMemberSecondProfile();

                    // 날짜 변환
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate = memberSecondProfile.getCreatedAt().format(formatter);

                    return MatchRequestDto.builder()
                            .memberProfileId(memberSecondProfile.getId())
                            .writerId(memberSecondProfile.getMember().getId())
                            .studentNumber(memberSecondProfile.getMember().getMemberProfile().getStudentNumber())
                            .major(memberSecondProfile.getMember().getMemberProfile().getSubMajor().getName())
                            .age(memberSecondProfile.getMember().getAge())
                            .mateType(memberSecondProfile.getMateType().getKoreanName())
                            .createdAt(formattedDate)
                            .build();
                })
                .collect(Collectors.toList());
    }


    private String getTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "알 수 없음";
        }

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(localDateTime, now);

        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (seconds < 60) {
            return seconds + "초 전";
        } else if (minutes < 60) {
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else if (days < 7) {
            return days + "일 전";
        } else {
            return localDateTime.toLocalDate().toString(); // "YYYY-MM-DD" 형식
        }
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
