package com.umc.meetpick.service.matching.factory;

import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import com.umc.meetpick.service.matching.strategy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchQueryStrategyFactory {

    private final MemberMappingRepository memberMappingRepository;

    public MatchQueryStrategy getStrategy(MateType mateType){
        return switch (mateType) {
            case ALL -> new MatchAllQueryStrategy(memberMappingRepository);
            case MEAL, EXERCISE, STUDY -> new MatchMateTypeQueryStrategy(memberMappingRepository);
        };
    }
}
