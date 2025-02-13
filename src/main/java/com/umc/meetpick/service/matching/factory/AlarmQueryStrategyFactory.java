package com.umc.meetpick.service.matching.factory;

import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import com.umc.meetpick.service.home.strategy.AllStrategy;
import com.umc.meetpick.service.home.strategy.MateStrategy;
import com.umc.meetpick.service.matching.strategy.AlarmAllQueryStrategy;
import com.umc.meetpick.service.matching.strategy.AlarmMateTypeQueryStrategy;
import com.umc.meetpick.service.matching.strategy.AlarmQueryStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmQueryStrategyFactory {

    private final MemberMappingRepository memberMappingRepository;

    public AlarmQueryStrategy getStrategy(MateType mateType){
        return switch (mateType) {
            case ALL -> new AlarmAllQueryStrategy(memberMappingRepository);
            case MEAL, EXERCISE, STUDY -> new AlarmMateTypeQueryStrategy(memberMappingRepository);
        };
    }
}
