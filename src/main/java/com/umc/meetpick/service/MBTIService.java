package com.umc.meetpick.service;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.common.response.status.SuccessCode;
import com.umc.meetpick.dto.MBTIDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfile;
import com.umc.meetpick.enums.MBTI;
import com.umc.meetpick.repository.MemberProfileRepository;
import com.umc.meetpick.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class MBTIService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;

    @Transactional
    public ApiResponse<MBTIDTO.MBTIResponseDTO> setMBTI(Long memberId, MBTIDTO.MBTIRequestDTO requestDTO) {
        if (requestDTO == null || requestDTO.getMBTI() == null) {
            return ApiResponse.onFailure(
                    ErrorCode.INVALID_MBTI.getCode(),
                    "MBTI 값이 비어 있습니다.",
                    null
            );
        }

        String mbtiString = requestDTO.getMBTI().toUpperCase();
        log.info("🔍 MBTI 설정 요청 - memberId={}, MBTI={}", memberId, mbtiString);

        // ✅ MBTI 유효성 검사
        MBTI mbtiEnum;
        try {
            mbtiEnum = MBTI.valueOf(mbtiString);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure(
                    ErrorCode.INVALID_MBTI.getCode(),
                    "유효하지 않은 MBTI 값입니다. (예: INTJ, ENFP 등)",
                    null
            );
        }

        // ✅ Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("❌ 해당 ID의 Member를 찾을 수 없습니다."));

        // ✅ MemberProfile 조회 (없으면 새로 생성)
        MemberProfile memberProfile = memberProfileRepository.findByMember(member)
                .orElseGet(() -> {
                    MemberProfile newProfile = MemberProfile.builder()
                            .member(member)
                            .nickname("Default Nickname")
                            .profileImage("default.png")
                            .studentNumber(0)
                            .MBTI(mbtiEnum)
                            .build();
                    memberProfileRepository.save(newProfile);
                    return newProfile;
                });

        // ✅ MBTI 업데이트
        memberProfile.setMBTI(mbtiEnum);
        memberProfileRepository.save(memberProfile);

        log.info("✅ MBTI 설정 완료 - memberId={}, profileId={}, MBTI={}", memberId, memberProfile.getId(), mbtiEnum);

        return ApiResponse.of(
                SuccessCode.MBTI_SET_SUCCESS,
                MBTIDTO.MBTIResponseDTO.builder()
                        .memberId(memberId)
                        .memberProfileId(memberProfile.getId())
                        .MBTI(mbtiEnum.name())
                        .message(mbtiEnum.name() + " 메이트이시군요!")
                        .build()
        );
    }
}

