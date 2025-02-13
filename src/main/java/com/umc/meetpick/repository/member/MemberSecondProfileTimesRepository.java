package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberSecondProfileTimesRepository extends JpaRepository<MemberSecondProfileTimes, Long> {

    List<MemberSecondProfileTimes> findByMemberSecondProfile(@Param("memberSecondProfile") MemberSecondProfile memberSecondProfile);

}
