package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.enums.MateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberMappingRepository extends JpaRepository<MemberSecondProfileMapping, Long> {

    Optional<MemberSecondProfileMapping> findByMemberSecondProfile(MemberSecondProfile memberSecondProfile);

    boolean existsByMemberSecondProfileAndMember(MemberSecondProfile memberSecondProfile, Member member);

    void deleteAllByMemberSecondProfile(MemberSecondProfile memberSecondProfile);

    Page<MemberSecondProfileMapping> findAllByMemberSecondProfile_MemberAndMemberSecondProfile_MateTypeAndIsAccepted(@Param("member")Member member, MateType mateType, Pageable pageable, @Param("isAccepted") Boolean isAccepted);

    Page<MemberSecondProfileMapping> findAllByMemberSecondProfile_MemberAndIsAccepted(@Param("member") Member member, Pageable pageable, @Param("isAccepted") boolean isAccepted);

    Optional<MemberSecondProfileMapping> findByMemberSecondProfileAndMember(@Param("memberSecondProfile") MemberSecondProfile memberSecondProfile, @Param("member") Member member);
}
