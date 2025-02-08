package com.umc.meetpick.service;

import com.umc.meetpick.dto.*;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.MateType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchingService {
    List<MatchResponseDto> match(Long memberId, MateType mateType);

    MatchRequestListDto getMatchRequests(Long memberId, Pageable pageable);

    List<AlarmResponseDto> getAlarms(Long memberId, MateType mateType);

    List<MatchRequestDto> getCompletedMatches(Long memberId, MateType mateType);

    ProfileDetailListResponseDto getAllProfiles(Long memberId, MateType mateType, FilterRequestDTO filterRequest, Pageable pageable);
}
