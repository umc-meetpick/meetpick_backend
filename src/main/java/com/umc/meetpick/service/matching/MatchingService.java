package com.umc.meetpick.service.matching;

import com.umc.meetpick.dto.*;
import com.umc.meetpick.enums.MateType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchingService {
    List<MatchResponseDto> match(Long memberId, MateType mateType);

    MatchRequestListDto getMatchRequests(Long memberId, Pageable pageable);

    AlarmDto.AlarmPageResponseDto getAlarms(String mateType, Pageable pageable, Long memberId);

    List<MatchRequestDto> getCompletedMatches(Long memberId, MateType mateType);
}
