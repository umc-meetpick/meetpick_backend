package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    boolean existsByNickname(String nickname);
    Optional<MemberProfile> findByMember(Member member);
    Optional<MemberProfile> findByMemberId(Long memberId);
    Optional<MemberProfile> findByNickname(String nickname);
}
