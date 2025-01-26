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
    private final RequestRepository requestRepository;
    private final NewRequestRepository newRequestRepository;
    private final MemberRepository memberRepository;
    private final RequestSubMajorRepository requestSubMajorRepository;
    private final SubMajorRepository subMajorRepository;
    private final MemberMappingRepository memberMappingRepository;
    private final MemberLikesRepository memberLikesRepository;

    @Override
    public RequestDTO.NewRequestDTO createNewRequest(RequestDTO.NewRequestDTO newRequest) {
        // 작성자가 실제 존재하는지 검증
        Member writer = memberRepository.findById(newRequest.getWriterId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다." + newRequest.getWriterId()));
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
        if (requestRepository.existsByWriterIdAndType(writer.getId(), newRequest.getType())) {
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

    @Override
    public void deleteRequest(Long requestId, Long userId){
        Request request = requestRepository.findById(requestId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 매칭에 대한 요청"));

        if (!request.getWriter().getId().equals(userId)) {
            throw new IllegalArgumentException("삭제 권한 없음");
        }
        requestRepository.delete(request);
    }

    // 매칭에 좋아요 등록
    @Override
    public RequestDTO.LikeRequestDTO likeRequest(Long requestId, Long userId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 매칭에 대한 요청"));

        Member member = memberRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 유저의 신청"));

        MemberLikes memberLikes = MemberLikes.builder()
                .member(member)
                .request(request)
                .build();

        MemberLikes savedMemberLikes = memberLikesRepository.save(memberLikes);

        return RequestDTO.LikeRequestDTO.builder()
                .requestId(savedMemberLikes.getRequest().getId())
                .postUserId(savedMemberLikes.getMember().getId())
                .build();
    }

    @Override
    public RequestDTO.isAcceptedDTO acceptRequest(Long requestId, Long userId, Boolean isAccepted) {

        Request request = requestRepository.findById(requestId)
                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 매칭"));

        MemberMapping memberMapping = memberMappingRepository.findByRequest(request)
                .orElseThrow(()-> new EntityNotFoundException("신청을 찾을 수 없음"));

        if(!memberMapping.getRequest().getWriter().getId().equals(userId)) {
            throw new IllegalArgumentException("권한 없음");
        }

        if(memberMapping.getStatus()){
            throw new IllegalArgumentException("이미 수락 or 거절됨");
        }

        memberMapping.setStatus(true);
        memberMapping.setIsAccepted(isAccepted);

        MemberMapping updatedMapping = memberMappingRepository.save(memberMapping);

        return RequestDTO.isAcceptedDTO.builder()
                .requestId(updatedMapping.getRequest().getId())
                .isAccepted(updatedMapping.getIsAccepted())
                .status(updatedMapping.getStatus())
                .build();


    }
}
