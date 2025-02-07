package com.umc.meetpick.service.home.template;


import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;

public interface MemberQueryStrategy {
    MemberSecondProfile findRandomMember(String mateType);
}
