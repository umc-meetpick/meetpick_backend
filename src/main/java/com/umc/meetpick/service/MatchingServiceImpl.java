package com.umc.meetpick.service;


import com.umc.meetpick.dto.MatchRequestDto;
import com.umc.meetpick.dto.MatchRequestListDto;
import com.umc.meetpick.dto.MatchResponseDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

    private final MemberSecondProfileRepository memberSecondProfileRepository;
    private final int recommendationNumber = 5;
    private final int minCondition = 3;
    private int page = 0;
    private int pageSize = 100;
    private Pageable pageable = PageRequest.of(page, pageSize);

    @Override
    public List<MatchResponseDto> match(Member member, MateType mateType){

        List<MatchResponseDto> matchResponseDtoList = new ArrayList<>();

        while(matchResponseDtoList.size() < recommendationNumber){

            List<MemberSecondProfile> requestList = getMatchingType(mateType);

            /*requestList.forEach(memberSecondProfile -> {

                int conditionMatching = 0;

                // 나이 조건 체크
                if((memberSecondProfile.getMinTime() <= member.getAge() && request.getMaxTime() >= member.getAge()) || request.getMinAge() == null){
                    conditionMatching++;
                }

                // 성별 조건 체크
                if(request.getGender() == member.getGender() || request.getGender() == null){
                    conditionMatching++;
                }

                // MBTI 조건 체크
                if(request.getMbti() != null && request.getMbti().contains(member.getMemberProfile().getMBTI()) || request.getMbti() == null){
                    conditionMatching++;
                }

                // 조건이 충족되면 matchResponseDtoList에 추가
                if(conditionMatching >= minCondition){
                    matchResponseDtoList.add(requestToMatchResponseDto(member, request));
                }
            });

            // 페이지를 넘어가면 다음 페이지로 이동
            if (matchResponseDtoList.size() < recommendationNumber) {
                page++;
                pageable = PageRequest.of(page, pageSize);
            }*/
        }

        return matchResponseDtoList;
    }

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

    /*private MatchResponseDto requestToMatchResponseDto(Member member, Request request){
        return MatchResponseDto.builder()
                .memberId(member.getId())
                .hobby(member.getMemberProfile().getHobby())
                .requestId(request.getId())
                .foodType(request.getFood())
                .gender(request.getGender())
                .hobby(request.getWriter().getMemberProfile().getHobby()) // 수정: writer를 memberProfile로 변경
                .build();
    }*/
}
