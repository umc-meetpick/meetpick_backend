package com.umc.meetpick.service;

import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.enums.MateType;

public interface MemberService {
    public MemberResponseDTO getRandomMember(MateType mateType);
}
