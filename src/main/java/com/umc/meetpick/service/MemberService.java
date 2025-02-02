package com.umc.meetpick.service;

import com.umc.meetpick.dto.MemberDetailResponseDto;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.enums.MateType;

import java.util.Map;

public interface MemberService {
    MemberResponseDTO getRandomMember(MateType mateType);

    MemberDetailResponseDto getMemberDetail(Long memberId);

    Map<String, String> getContactInfo(Long memberId);
}
