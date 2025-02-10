package com.umc.meetpick.service.matching.strategy;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchQueryStrategy {
    Page<MemberSecondProfile> getMemberProfiles(Pageable pageable);
}
