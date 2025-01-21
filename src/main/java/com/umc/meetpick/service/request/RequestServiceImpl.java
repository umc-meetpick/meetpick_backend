package com.umc.meetpick.service.request;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.entity.*;
import com.umc.meetpick.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final NewRequestRepository newRequestRepository;
    private final MemberRepository memberRepository;
    private final RequestSubMajorRepository requestSubMajorRepository;
    private final SubMajorRepository subMajorRepository;

    @Override
    public RequestDTO.NewRequestDTO createNewRequest(RequestDTO.NewRequestDTO newRequest) {
        // 작성자가 실제 존재하는지 검증
        Member writer = memberRepository.findById(newRequest.getWriterId())
                .orElseThrow(()-> new EntityNotFoundException("사용자를 찾을 수 없습니다." + newRequest.getWriterId()));
        // 프론트로부터 받은 전공이 전공 테이블에 존재하는지 검증
//        Major major = majorRepository.findByName(newRequest.getMajorName())
//                .orElseThrow(()-> new EntityNotFoundException("등록된 전공이 아닙니다." + newRequest.getMajorName()));

        // 나이 범위 검증
        if (newRequest.getMinAge() >= newRequest.getMaxAge()) {
            throw new IllegalArgumentException("나이 범위 에러");
        }

        // 시간 범위 검증
        if (newRequest.getMinTime() >= newRequest.getMaxTime()) {
            throw new IllegalArgumentException("시간 범위 에러");
        }

        // 동일 작성자 동일 타입 중복 요청 체크
        if (newRequestRepository.existsByWriterIdAndType(writer.getId(), newRequest.getType())){
            throw new IllegalArgumentException("이미 요청이 존재");
        }

        // 새로운 Request 생성
        Request request = Request.builder()
                .writer(writer)
                //.major(major)
                .studentNumber(newRequest.getStudentNumber())
                .mbti(newRequest.getMbti())
                .minAge(newRequest.getMinAge())
                .maxAge(newRequest.getMaxAge())
                .minTime(newRequest.getMinTime())
                .maxTime(newRequest.getMaxTime())
                .food(newRequest.getFood())
                .maxPeople(newRequest.getMaxPeople())
                .currentPeople(0)
                .type(newRequest.getType())
                .build();

        Request savedRequest = newRequestRepository.save(request);

        Set<RequestSubMajor> subMajorNames = newRequest.getSubMajorName().stream()
                .map(name -> createRequestSubMajor(name, savedRequest))  // 함수 사용
                .collect(Collectors.toSet());

        requestSubMajorRepository.saveAll(subMajorNames);  // 여러 개의 엔티티를 한번에 저장


        return RequestDTO.NewRequestDTO.builder()
                .writerId(savedRequest.getWriter().getId())
                .studentNumber(savedRequest.getStudentNumber())
                .mbti(savedRequest.getMbti())
                .minAge(savedRequest.getMinAge())
                .maxAge(savedRequest.getMaxAge())
                .minTime(savedRequest.getMinTime())
                .maxTime(savedRequest.getMaxTime())
                .food(savedRequest.getFood())
                .maxPeople(savedRequest.getMaxPeople())
                .type(savedRequest.getType())
                .build();
    }

    private RequestSubMajor createRequestSubMajor(String name, Request request) {

        SubMajor subMajor = subMajorRepository.findByName(name).orElseThrow(()-> new GeneralHandler(ErrorCode._BAD_REQUEST));

        RequestSubMajor requestSubMajor = RequestSubMajor.builder()
                .subMajor(subMajor)
                .request(request)
                .build();

        return requestSubMajorRepository.save(requestSubMajor);
    }
}
