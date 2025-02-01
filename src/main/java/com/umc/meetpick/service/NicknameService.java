package com.umc.meetpick.service;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.common.response.status.SuccessCode;
import com.umc.meetpick.dto.NicknameDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfile;
import com.umc.meetpick.repository.MemberProfileRepository;
import com.umc.meetpick.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NicknameService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;

    /**
     * 🔍 닉네임 중복 확인 API
     */
    public ApiResponse<NicknameDTO.NicknameCheckResponseDTO> checkNicknameAvailability(Long memberId, String nickname) {
        boolean exists = memberProfileRepository.existsByNickname(nickname);
        boolean isAvailable = !exists || (memberId != null && memberProfileRepository.findByMemberId(memberId)
                .map(profile -> profile.getNickname().equals(nickname))
                .orElse(false));

        if (!isAvailable) {
            return ApiResponse.onFailure(
                    ErrorCode.NICKNAME_ALREADY_EXISTS.getCode(),
                    ErrorCode.NICKNAME_ALREADY_EXISTS.getMessage(),
                    NicknameDTO.NicknameCheckResponseDTO.builder()
                            .isAvailable(false)
                            .build()
            );
        }

        return ApiResponse.of(
                SuccessCode.NICKNAME_AVAILABLE,
                NicknameDTO.NicknameCheckResponseDTO.builder()
                        .isAvailable(true)
                        .build()
        );
    }

    /**
     * ✅ 닉네임 설정 API
     */
    @Transactional
    public ApiResponse<NicknameDTO.NicknameResponseDTO> setNickname(Long memberId, NicknameDTO.NicknameRequestDTO requestDTO) {
        String nickname = requestDTO.getNickname();
        log.info("🔍 닉네임 설정 요청 - memberId={}, nickname={}", memberId, nickname);

        // 중복 닉네임 검사
        if (memberProfileRepository.existsByNickname(nickname)) {
            return ApiResponse.onFailure(
                    ErrorCode.NICKNAME_ALREADY_EXISTS.getCode(),
                    ErrorCode.NICKNAME_ALREADY_EXISTS.getMessage(),
                    null
            );
        }

        // Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("❌ 해당 ID의 Member를 찾을 수 없습니다."));

        // MemberProfile 조회 (없으면 새로 생성)
        MemberProfile memberProfile = memberProfileRepository.findByMemberId(memberId)
                .orElseGet(() -> {
                    MemberProfile newProfile = MemberProfile.builder()
                            .member(member)
                            .nickname(nickname)
                            .profileImage("default.png")
                            .build();
                    memberProfileRepository.save(newProfile);
                    return newProfile;
                });

        // 닉네임 업데이트
        memberProfile.setNickname(nickname);
        memberProfileRepository.save(memberProfile);

        // Member 테이블의 member_profile 컬럼 업데이트
        if (member.getMemberProfile() == null || !member.getMemberProfile().getId().equals(memberProfile.getId())) {
            member.setMemberProfile(memberProfile);
            memberRepository.save(member);
        }

        log.info("✅ 닉네임 설정 완료 - memberId={}, profileId={}, nickname={}", memberId, memberProfile.getId(), nickname);
        return ApiResponse.of(
                SuccessCode.NICKNAME_SET_SUCCESS,
                new NicknameDTO.NicknameResponseDTO(memberId, memberProfile.getId(), nickname)
        );
    }
}
