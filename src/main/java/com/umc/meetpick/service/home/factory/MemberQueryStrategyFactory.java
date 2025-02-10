package com.umc.meetpick.service.home.factory;

import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import com.umc.meetpick.service.home.strategy.AllStrategy;
import com.umc.meetpick.service.home.strategy.MateStrategy;
import com.umc.meetpick.service.home.strategy.MemberQueryStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberQueryStrategyFactory {

    private final MemberSecondProfileRepository memberSecondProfileRepository;

    public MemberQueryStrategy getStrategy(MateType mateType) {

        return switch (mateType) {
            case ALL -> new AllStrategy(memberSecondProfileRepository);
            case MEAL, EXERCISE, STUDY -> new MateStrategy(memberSecondProfileRepository);
        };
    }
}
