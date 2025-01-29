package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberSecondProfileRepository extends JpaRepository<MemberSecondProfile, Long> {

    Optional<MemberSecondProfile> findMemberSecondProfileByIdAndMateType(@Param("id") Long id, @Param("mateType") MateType mateType);
}
