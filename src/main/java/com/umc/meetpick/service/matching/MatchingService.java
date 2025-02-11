package com.umc.meetpick.service.matching;

import com.umc.meetpick.dto.*;
import com.umc.meetpick.enums.MateType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchingService {
    List<MatchResponseDto> match(Long memberId, MateType mateType);

    MatchPageDto getMatchRequests(Long memberId, String mateType, Pageable pageable);

    AlarmDto.AlarmPageResponseDto getAlarms(String mateType, Pageable pageable, Long memberId);

    MatchPageDto getCompletedMatches(Long memberId, String mateType, Pageable pageable);
}
