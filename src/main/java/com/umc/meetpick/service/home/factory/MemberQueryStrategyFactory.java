package com.umc.meetpick.service.home.factory;

import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import com.umc.meetpick.service.home.template.AllStrategy;
import com.umc.meetpick.service.home.template.MateStrategy;
import com.umc.meetpick.service.home.template.MemberQueryStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberQueryStrategyFactory {

    private final MemberSecondProfileRepository memberSecondProfileRepository;


    public MemberQueryStrategy getStrategy(String mateType) {

        MateType type = MateType.fromString(mateType);

        return switch (type) {
            case ALL -> new AllStrategy(memberSecondProfileRepository);
            case MEAL, EXERCISE, STUDY -> new MateStrategy(memberSecondProfileRepository);
            default -> throw new IllegalArgumentException("Unsupported mateType: " + type);
        };
    }
}
