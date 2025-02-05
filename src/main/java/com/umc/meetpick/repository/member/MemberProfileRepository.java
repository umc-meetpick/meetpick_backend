package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

    Optional<MemberProfile> findByNickname(@Param("nickname") String nickname);
}
