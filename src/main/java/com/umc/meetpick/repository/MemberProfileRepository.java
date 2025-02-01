package com.umc.meetpick.repository;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    boolean existsByNickname(String nickname);
    Optional<MemberProfile> findByMember(Member member);
    Optional<MemberProfile> findByMemberId(Long memberId);

}
