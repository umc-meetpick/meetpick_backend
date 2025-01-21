package com.umc.meetpick.service;

import com.umc.meetpick.dto.MatchRequestDto;
import com.umc.meetpick.dto.MatchResponseDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.MateType;

import java.util.List;

public interface MatchingService {
    public List<MatchResponseDto> match(Member member, MateType mateType);
}
