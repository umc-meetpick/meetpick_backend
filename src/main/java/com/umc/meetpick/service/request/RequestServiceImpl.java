package com.umc.meetpick.service.request;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.entity.*;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileLikes;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.entity.mapping.MemberSecondProfileSubMajor;
import com.umc.meetpick.entity.mapping.MemberSecondProfileTimes;
import com.umc.meetpick.repository.*;
import com.umc.meetpick.repository.member.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final NewRequestRepository newRequestRepository;
    private final MemberRepository memberRepository;
    private final SubMajorRepository subMajorRepository;
    private final MemberMappingRepository memberMappingRepository;
    private final MemberLikesRepository memberLikesRepository;
    private final MemberSecondProfileRepository memberSecondProfileRepository;
    private final MemberSecondProfileTimesRepository memberSecondProfileTimesRepository;

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

        // 동일 작성자 동일 타입 중복 요청 체크
        if (memberSecondProfileRepository.existsByMemberIdAndMateType(writer.getId(), newRequest.getType())) {
            throw new IllegalArgumentException("이미 요청이 존재");
        }

        // 새로운 MemberSecondProfile 생성
        MemberSecondProfile newMemberSecondProfile = MemberSecondProfile.builder()
                .member(writer)
                .gender(newRequest.getGender())
                .studentNumber(newRequest.getStudentNumber())
                .mbti(newRequest.getMbti())
                .minAge(newRequest.getMinAge())
                .maxAge(newRequest.getMaxAge())
                .maxPeople(newRequest.getMaxPeople())
                .currentPeople(0)
                .mateType(newRequest.getType())
                .build();

        MemberSecondProfile savedProfile = memberSecondProfileRepository.save(newMemberSecondProfile);

        // membersecondprofiletimesd dto를 entity로 변환
        List<MemberSecondProfileTimes> timesList = newRequest.getMemberSecondProfileTimes().stream()
                .map(dto -> MemberSecondProfileTimes.builder()
                        .week(dto.getWeek())
                        .times(dto.getTimes())
                        .memberSecondProfile(savedProfile) // 프로필과 연결
                        .build())
                .toList();

        memberSecondProfileTimesRepository.saveAll(timesList);

        List<MemberSecondProfileSubMajor> subMajorNames = newRequest.getSubMajorName().stream()
                .map(dto -> {
                    // 프론트에서 받은 subMajorName으로 SubMajor entity 찾기
                    SubMajor subMajor = subMajorRepository.findByName(dto.getSubMajor().getName())
                            .orElseThrow(()-> new EntityNotFoundException("등록 전공 아님"));

                    // MemberSecondProfileSubMajor 생성
                    return MemberSecondProfileSubMajor.builder()
                            .memberSecondProfile(savedProfile)
                            .subMajor(subMajor)
                            .build();
                })
                        .toList();

        return RequestDTO.NewRequestDTO.builder()
                .writerId(savedProfile.getMember().getId())
                .studentNumber(savedProfile.getStudentNumber())
                .mbti(savedProfile.getMbti())
                .minAge(savedProfile.getMinAge())
                .maxAge(savedProfile.getMaxAge())
                .maxPeople(savedProfile.getMaxPeople())
                .type(savedProfile.getMateType())
                .build();

    }

    @Override
    public RequestDTO.JoinRequestDTO createJoinRequest(RequestDTO.JoinRequestDTO newJoinRequest) {
        // requestId가 존재하는지 판단
        MemberSecondProfile request = memberSecondProfileRepository.findById(newJoinRequest.getRequestId())
                .orElseThrow(()-> new EntityNotFoundException("잘못된 매칭에 대한 요청" + newJoinRequest.getRequestId()));

        request.addPerson();

        // 유저 join으로 찾기
        Member member = memberRepository.findById(newJoinRequest.getPostUserId())
                .orElseThrow(() -> new EntityNotFoundException("매칭 신청 유저 오류" + newJoinRequest.getPostUserId()));

        // MemberMapping 생성
        MemberSecondProfileMapping memberSecondProfileMapping = MemberSecondProfileMapping.builder()
                .member(member)
                .memberSecondProfile(request)
                .status(false)
                .build();
        MemberSecondProfileMapping savedMapping = memberMappingRepository.save(memberSecondProfileMapping);

        return RequestDTO.JoinRequestDTO.builder()
                .requestId(savedMapping.getMemberSecondProfile().getId())
                .postUserId(savedMapping.getMember().getId())
                .status(savedMapping.getStatus())
                .build();

    }

    @Override
    public void deleteRequest(Long requestId, Long userId){
        MemberSecondProfile request = memberSecondProfileRepository.findById(requestId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 매칭에 대한 요청"));

        if (!request.getMember().getId().equals(userId)) {
            throw new IllegalArgumentException("삭제 권한 없음");
        }
        memberSecondProfileRepository.delete(request);
    }

    // 매칭에 좋아요 등록
    @Override
    public RequestDTO.LikeRequestDTO likeRequest(Long requestId, Long userId) {
        MemberSecondProfile request = memberSecondProfileRepository.findById(requestId)
                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 매칭에 대한 요청"));

        Member member = memberRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 유저의 신청"));

        MemberSecondProfileLikes memberLikes = MemberSecondProfileLikes.builder()
                .member(member)
                .memberSecondProfile(request)
                .build();

        MemberSecondProfileLikes savedMemberLikes = memberLikesRepository.save(memberLikes);

        return RequestDTO.LikeRequestDTO.builder()
                .requestId(savedMemberLikes.getMemberSecondProfile().getId())
                .postUserId(savedMemberLikes.getMember().getId())
                .build();
    }

    @Override
    public void deleteLikeRequest(Long requestId, Long userId){

        MemberSecondProfileLikes memberLikes = memberLikesRepository.findByMemberSecondProfileId(requestId)
                .orElseThrow(()->new IllegalArgumentException("찜하기 찾을 수 없음"));

        if (!memberLikes.getMember().getId().equals(userId)) {
            throw new IllegalArgumentException("취소 권한 없음");
        }
        memberLikesRepository.delete(memberLikes);
    }
}
