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

    Page<MemberSecondProfileMapping> findAllByMemberSecondProfile_Member(@Param("member") Member member, Pageable pageable);

    //TODO 쿼리가 좀 복잡한 것 같은데... 일단 스킵
    Page<MemberSecondProfileMapping> findAllByMemberSecondProfile_MemberAndMemberSecondProfile_MateType(@Param("member") Member member, MateType mateType, Pageable pageable);

    Page<MemberSecondProfileMapping> findAllByMemberSecondProfile_MemberAndMemberSecondProfile_MateTypeAndIsAcceptedIsFalse(@Param("member")Member member, MateType mateType, Pageable pageable);

    Page<MemberSecondProfileMapping> findAllByMemberSecondProfile_MemberAndIsAcceptedIsFalse(@Param("member") Member member, Pageable pageable);
}
