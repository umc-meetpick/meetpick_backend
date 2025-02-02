package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.mapping.MemberSecondProfileTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberSecondProfileTimesRepository extends JpaRepository<MemberSecondProfileTimes, Long> {
}
