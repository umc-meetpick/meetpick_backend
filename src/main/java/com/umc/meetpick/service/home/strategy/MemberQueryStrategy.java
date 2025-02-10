package com.umc.meetpick.service.home.strategy;


import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;

public interface MemberQueryStrategy {
    MemberSecondProfile findRandomMember(MateType mateType);
}
