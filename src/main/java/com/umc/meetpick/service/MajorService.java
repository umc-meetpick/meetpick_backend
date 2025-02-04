package com.umc.meetpick.service;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.common.response.status.SuccessCode;
import com.umc.meetpick.dto.MajorDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.Major;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.SubMajor;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.SubMajorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MajorService {

    private final MemberRepository memberRepository;
    private final SubMajorRepository subMajorRepository;

    @Transactional
    public ApiResponse<MajorDTO.MajorResponseDTO> setMajor(Long memberId, MajorDTO.MajorRequestDTO requestDTO) {
        Long subMajorId = requestDTO.getSubMajorId();
        log.info("🔍 전공 설정 요청 - memberId={}, subMajorId={}", memberId, subMajorId);

        // 전공(학과) 조회
        SubMajor subMajor = subMajorRepository.findById(subMajorId)
                .orElseThrow(() -> new RuntimeException(ErrorCode.SUB_MAJOR_NOT_FOUND.getMessage()));

        Major major = subMajor.getMajor(); // 전공 계열 가져오기

        // 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(ErrorCode.MEMBER_NOT_FOUND.getMessage()));

        // ✅ MemberProfile 가져오기
        MemberProfile memberProfile = member.getMemberProfile();
        if (memberProfile == null) {
            throw new RuntimeException("❌ MemberProfile이 존재하지 않습니다.");
        }

        // ✅ 전공 설정을 MemberProfile에 저장
        memberProfile.setMajor(major);
        //memberProfile.setSubMajor(subMajor); // SubMajor도 설정

        log.info("✅ 전공 설정 완료 - memberId={}, subMajorId={}, subMajorName={}, majorId={}, majorName={}",
                memberId, subMajor.getId(), subMajor.getName(), major.getId(), major.getName());

        return ApiResponse.of(
                SuccessCode.MAJOR_SET_SUCCESS,
                new MajorDTO.MajorResponseDTO(memberId, subMajor.getId(), subMajor.getName(), major.getId(), major.getName())
        );
    }
}
