package com.umc.meetpick.service;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.common.response.status.SuccessCode;
import com.umc.meetpick.dto.StudentNumberDTO;
import com.umc.meetpick.entity.Member;

import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.repository.member.MemberProfileRepository;
import com.umc.meetpick.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentNumberService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;

    /**
     * ✅ 학번 설정 API (문자열 검증 추가)
     */
    @Transactional
    public ApiResponse<StudentNumberDTO.StudentNumberResponseDTO> setStudentNumber(Long memberId, StudentNumberDTO.StudentNumberRequestDTO requestDTO) {
        String studentNumberStr = requestDTO.getStudentNumber();
        log.info("🔍 학번 설정 요청 - memberId={}, studentNumber={}", memberId, studentNumberStr);

        // 숫자 검증 (추가)
        if (studentNumberStr == null || !studentNumberStr.matches("\\d+")) {
            return ApiResponse.onFailure(
                    ErrorCode.INVALID_STUDENT_NUMBER.getCode(),
                    ErrorCode.INVALID_STUDENT_NUMBER.getMessage(),
                    null
            );
        }

        int studentNumber = Integer.parseInt(studentNumberStr); // 숫자로 변환

        // Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("❌ 해당 ID의 Member를 찾을 수 없습니다."));

        // MemberProfile 조회 (없으면 새로 생성)
        MemberProfile memberProfile = memberProfileRepository.findByMember(member)
                .orElseGet(() -> {
                    MemberProfile newProfile = MemberProfile.builder()
                            .member(member)
                            .nickname("Default Nickname")
                            .profileImage("default.png")
                            .studentNumber(studentNumber)
                            .build();
                    memberProfileRepository.save(newProfile);
                    return newProfile;
                });

        // 학번 업데이트
        memberProfile.setStudentNumber(studentNumber);
        memberProfileRepository.save(memberProfile);

        // Member 테이블의 member_profile 컬럼 업데이트
        if (member.getMemberProfile() == null || !member.getMemberProfile().getId().equals(memberProfile.getId())) {
            member.setMemberProfile(memberProfile);
            memberRepository.save(member);
        }

        log.info("✅ 학번 설정 완료 - memberId={}, profileId={}, studentNumber={}", memberId, memberProfile.getId(), studentNumber);
        return ApiResponse.of(
                SuccessCode.STUDENT_NUMBER_SET_SUCCESS,
                new StudentNumberDTO.StudentNumberResponseDTO(memberId, memberProfile.getId(), studentNumber)
        );
    }
}
