package com.umc.meetpick.repository;

import com.umc.meetpick.entity.KakaoMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KakaoMemberRepository extends JpaRepository<KakaoMember, Long> {

    // ✅ 기존 findBySocialId에 fetch join 추가
    @Query("SELECT km FROM KakaoMember km LEFT JOIN FETCH km.member WHERE km.socialId = :socialId")
    Optional<KakaoMember> findBySocialId(@Param("socialId") Long socialId);
}
