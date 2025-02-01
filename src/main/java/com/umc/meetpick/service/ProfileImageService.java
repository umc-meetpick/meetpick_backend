package com.umc.meetpick.service;

import com.umc.meetpick.dto.ProfileImageDTO;
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
public class ProfileImageService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;

    @Transactional
    public ProfileImageDTO.ProfileImageResponseDTO setProfileImage(Long memberId, ProfileImageDTO.ProfileImageRequestDTO requestDTO) {
        log.info("🔍 프로필 이미지 업데이트 요청 - memberId={}, imageUrl={}", memberId, requestDTO.getImageUrl());

        // 1️⃣ Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("❌ 해당 ID의 Member를 찾을 수 없습니다."));

        // 2️⃣ MemberProfile 조회 (없으면 새로 생성)
        MemberProfile memberProfile = memberProfileRepository.findByMember(member)
                .orElseGet(() -> {
                    log.warn("⚠ memberId={}의 프로필이 존재하지 않음, 새로 생성", memberId);
                    MemberProfile newProfile = MemberProfile.builder()
                            .member(member)
                            .profileImage("default.png")
                            .build();
                    memberProfileRepository.save(newProfile);

                    // member와 연결
                    member.setMemberProfile(newProfile);
                    memberRepository.save(member);

                    return newProfile;
                });

        // 3️⃣ 프로필 이미지 업데이트
        memberProfile.setProfileImage(requestDTO.getImageUrl());
        memberProfileRepository.save(memberProfile);

        log.info("✅ 프로필 이미지 업데이트 완료 - memberId={}, profileImage={}, memberProfileId={}",
                memberId, requestDTO.getImageUrl(), memberProfile.getId());

        return ProfileImageDTO.ProfileImageResponseDTO.builder()
                .memberId(memberId)
                .memberProfileId(memberProfile.getId())
                .profileImage(memberProfile.getProfileImage())
                .build();
    }
}
