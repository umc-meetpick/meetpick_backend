package com.umc.meetpick.service;

import com.umc.meetpick.dto.AlarmResponseDto;
import com.umc.meetpick.dto.MatchRequestDto;
import com.umc.meetpick.dto.MatchRequestListDto;
import com.umc.meetpick.dto.MatchResponseDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.MateType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchingService {
    List<MatchResponseDto> match(Member member, MateType mateType);

    MatchRequestListDto getMatchRequests(Long memberId, Pageable pageable);

    List<AlarmResponseDto> getAlarms(Member member, MateType mateType);
}
