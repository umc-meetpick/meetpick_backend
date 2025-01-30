package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberMappingRepository extends JpaRepository<MemberSecondProfileMapping, Long> {
    Optional<MemberSecondProfileMapping> findByMemberSecondProfile(MemberSecondProfile memberSecondProfile);
}
