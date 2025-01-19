package com.umc.meetpick.service.request;

import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.entity.*;
import com.umc.meetpick.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final NewRequestRepository newRequestRepository;
    private final MemberRepository memberRepository;
    private final MajorRepository majorRepository;
    private final SubMajorRepository subMajorRepository;
    private final HobbyRepository hobbyRepository;

    @Override
    public Request createNewRequest(RequestDTO.NewRequestDTO newRequest) {
        // 작성자가 실제 존재하는지 검증
        Member writer = memberRepository.findById(newRequest.getWriterId())
                .orElseThrow(()-> new EntityNotFoundException("사용자를 찾을 수 없습니다." + newRequest.getWriterId()));
        // 프론트로부터 받은 전공이 전공 테이블에 존재하는지 검증
        Major major = majorRepository.findByName(newRequest.getMajorName())
                .orElseThrow(()-> new EntityNotFoundException("등록된 전공이 아닙니다." + newRequest.getMajorName()));
        // 프론트로부터 받은 서브전공이 서브전공 테이블에 존재하는지 검증
        //SubMajor subMajor = subMajorRepository.findByName(newRequest.getSubMajorName())
                //.orElseThrow(()-> new EntityNotFoundException("등록된 전공이 아닙니다." + newRequest.getMajorName()));

        // 프론트로부터 받은 취미가 취미 테이블에 존재하는지 검증
        Hobby hobby = hobbyRepository.findByName(newRequest.getHobbyName())
                .orElseThrow(()->new EntityNotFoundException("등록된 취미가 아닙니다." + newRequest.getHobbyName()));

        // 새로운 Request 생성
        Request request = Request.builder()
                .writer(writer)
                .major(major)
                //.subMajor(subMajor)
                .hobby(hobby)
                .studentNumber(newRequest.getStudentNumber())
                .mbti(newRequest.getMbti())
                .minAge(newRequest.getMinAge())
                .maxAge(newRequest.getMaxAge())
                .minTime(newRequest.getMinTime())
                .maxTime(newRequest.getMaxTime())
                .food(newRequest.getFood())
                .maxPeople(newRequest.getMaxPeople())
                .currentPeople(newRequest.getCurrentPeople())
                .type(newRequest.getType())
                .build();
        return newRequestRepository.save(request);
    }
}
