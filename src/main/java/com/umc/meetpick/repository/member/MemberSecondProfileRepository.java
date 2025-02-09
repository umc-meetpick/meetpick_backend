package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.enums.MateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberSecondProfileRepository extends JpaRepository<MemberSecondProfile, Long> {

    boolean existsByMemberIdAndMateType(Long writerId, MateType mateType);

    boolean existsByMemberId(Long writerid);

    Page<MemberSecondProfile> findMemberSecondProfilesByMateType(@Param("memberId") MateType mateType, Pageable pageable);

    @Query("SELECT m FROM MemberSecondProfile m " +
            "JOIN MemberSecondProfileMapping mm ON m = mm.memberSecondProfile " +
            "WHERE m.member.id = :memberId " +
            "ORDER BY m.createdAt DESC")
    Page<MemberSecondProfile> findMemberSecondProfileByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    Optional<MemberSecondProfile> findFirstByMateTypeOrderByCreatedAtDesc(@Param("mateType") MateType mateType);

    Optional<MemberSecondProfile> findFirstBy();


}
