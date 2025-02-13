package com.umc.meetpick.service.matching.strategy;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.enums.MateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AlarmQueryStrategy {
    Page<MemberSecondProfileMapping> getSecondProfilesByMateType(Member member, MateType mateType, Pageable pageable);
}
