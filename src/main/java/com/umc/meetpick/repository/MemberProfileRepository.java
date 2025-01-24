package com.umc.meetpick.repository;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    // 특정 Member와 연결된 MemberProfile 조회
    Optional<MemberProfile> findByMember(Member member);
}
