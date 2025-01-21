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
    private final RequestRepository requestRepository;
    private final MemberRepository memberRepository;
    //private final MajorRepository majorRepository;
    private final SubMajorRepository subMajorRepository;
    private final HobbyRepository hobbyRepository;
    private final MemberMappingRepository memberMappingRepository;

    @Override
    public RequestDTO.NewRequestDTO createNewRequest(RequestDTO.NewRequestDTO newRequest) {
        // 작성자가 실제 존재하는지 검증
        Member writer = memberRepository.findById(newRequest.getWriterId())
                .orElseThrow(()-> new EntityNotFoundException("사용자를 찾을 수 없습니다." + newRequest.getWriterId()));
        // 프론트로부터 받은 전공이 전공 테이블에 존재하는지 검증
//        Major major = majorRepository.findByName(newRequest.getMajorName())
//                .orElseThrow(()-> new EntityNotFoundException("등록된 전공이 아닙니다." + newRequest.getMajorName()));

        // 프론트로부터 받은 서브전공이 서브전공 테이블에 존재하는지 검증
        SubMajor subMajor = subMajorRepository.findByName(newRequest.getSubMajorName())
                .orElseThrow(()-> new EntityNotFoundException("등록된 전공이 아닙니다." + newRequest.getSubMajorName()));

        // 프론트로부터 받은 취미가 취미 테이블에 존재하는지 검증
        Hobby hobby = hobbyRepository.findByName(newRequest.getHobbyName())
                .orElseThrow(()->new EntityNotFoundException("등록된 취미가 아닙니다." + newRequest.getHobbyName()));

        // 나이 범위 검증
        if (newRequest.getMinAge() >= newRequest.getMaxAge()) {
            throw new IllegalArgumentException("나이 범위 에러");
        }

        // 시간 범위 검증
        if (newRequest.getMinTime() >= newRequest.getMaxTime()) {
            throw new IllegalArgumentException("시간 범위 에러");
        }

        // 동일 작성자 동일 타입 중복 요청 체크
        if (requestRepository.existsByWriterIdAndType(writer.getId(), newRequest.getType())){
            throw new IllegalArgumentException("이미 요청이 존재");
        }

        // 새로운 Request 생성
        Request request = Request.builder()
                .writer(writer)
                //.major(major)
                .subMajor(subMajor)
                .hobby(hobby)
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
        Request savedRequest = requestRepository.save(request);

        return RequestDTO.NewRequestDTO.builder()
                .writerId(savedRequest.getWriter().getId())
                .subMajorName(savedRequest.getSubMajor().getName())
                .hobbyName(savedRequest.getHobby().getName())
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

    @Override
    public RequestDTO.JoinRequestDTO createJoinRequest(RequestDTO.JoinRequestDTO newJoinRequest) {
        // requestId가 존재하는지 판단
        Request request = requestRepository.findById(newJoinRequest.getRequestId())
                .orElseThrow(()-> new EntityNotFoundException("잘못된 매칭에 대한 요청" + newJoinRequest.getRequestId()));

        request.addPerson();

        // 유저 join으로 찾기
        Member member = memberRepository.findById(newJoinRequest.getPostUserId())
                .orElseThrow(() -> new EntityNotFoundException("매칭 신청 유저 오류" + newJoinRequest.getPostUserId()));

        // MemberMapping 생성
        MemberMapping memberMapping = MemberMapping.builder()
                .member(member)
                .request(request)
                .status(false)
                .build();
        MemberMapping savedMapping = memberMappingRepository.save(memberMapping);

        return RequestDTO.JoinRequestDTO.builder()
                .requestId(savedMapping.getRequest().getId())
                .postUserId(savedMapping.getMember().getId())
                .status(savedMapping.getStatus())
                .build();
    }
}
