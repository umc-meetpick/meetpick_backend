package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
}
