package com.umc.meetpick.service;

import com.umc.meetpick.dto.MatchResponseDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.Request;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

    private final RequestRepository requestRepository;
    private final int recommendationNumber = 5;
    private final int minCondition = 3;
    private int page = 0;
    private int pageSize = 100;
    private Pageable pageable = PageRequest.of(page, pageSize);

    @Override
    public List<MatchResponseDto> match(Member member, MateType mateType){

        List<MatchResponseDto> matchResponseDtoList = new ArrayList<>();

        while(matchResponseDtoList.size() < recommendationNumber){
            List<Request> requestList = getMatchingType(mateType);

            requestList.forEach(request -> {

                int conditionMatching = 0;

                // 나이 조건 체크
                if((request.getMinTime() <= member.getAge() && request.getMaxTime() >= member.getAge()) || request.getMinAge() == null){
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
            }
        }

        return matchResponseDtoList;
    }

    private List<Request> getMatchingType(MateType mateType){
        return requestRepository.findAllByType(mateType, pageable).getContent();
    }

    private MatchResponseDto requestToMatchResponseDto(Member member, Request request){
        return MatchResponseDto.builder()
                .memberId(member.getId())
                .hobby(member.getMemberProfile().getHobby())
                .requestId(request.getId())
                .foodType(request.getFood())
                .gender(request.getGender())
                .hobby(request.getWriter().getMemberProfile().getHobby()) // 수정: writer를 memberProfile로 변경
                .build();
    }
}
