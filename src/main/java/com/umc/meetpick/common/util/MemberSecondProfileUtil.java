package com.umc.meetpick.common.util;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;

import java.util.List;
import java.util.Optional;

public class MemberSecondProfileUtil {
    public static Optional<MemberSecondProfile> findByMateType(List<MemberSecondProfile> profiles, MateType mateType) {
        return profiles.stream()
                .filter(profile -> mateType.equals(profile.getMateType()))
                .findFirst();
    }
}
