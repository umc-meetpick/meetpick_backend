package com.umc.meetpick.service;

import com.umc.meetpick.dto.MatchRequestDto;
import com.umc.meetpick.dto.MatchRequestListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  // 읽기 전용 트랜잭션 설정
public class RequestService {

    /*private final RequestRepository requestRepository;

    public MatchRequestListDto getMatchRequests(Long memberId, Pageable pageable) {
        // 1. 페이징된 Request 엔티티 조회
        Page<Request> requests = requestRepository.findAllRequestsByMemberId(memberId, pageable);

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
    }*/
}