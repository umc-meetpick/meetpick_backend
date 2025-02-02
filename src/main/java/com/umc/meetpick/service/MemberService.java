package com.umc.meetpick.service;

import com.umc.meetpick.dto.MemberDetailResponseDto;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.enums.MateType;

public interface MemberService {
    MemberResponseDTO getRandomMember(MateType mateType);

    MemberDetailResponseDto getMemberDetail(Long memberId);
}
