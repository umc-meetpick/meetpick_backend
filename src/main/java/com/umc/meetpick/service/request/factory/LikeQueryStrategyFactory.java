package com.umc.meetpick.service.request.factory;

import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberLikesRepository;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import com.umc.meetpick.service.matching.strategy.MatchAllQueryStrategy;
import com.umc.meetpick.service.matching.strategy.MatchMateTypeQueryStrategy;
import com.umc.meetpick.service.matching.strategy.MatchQueryStrategy;
import com.umc.meetpick.service.request.strategy.ExerciseLikeQueryStrategy;
import com.umc.meetpick.service.request.strategy.FoodLikeQueryStrategy;
import com.umc.meetpick.service.request.strategy.LikeQueryStrategy;
import com.umc.meetpick.service.request.strategy.StudyLikeQueryStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeQueryStrategyFactory {

    private final MemberLikesRepository memberLikesRepository;

    public LikeQueryStrategy getStrategy(MateType mateType){
        return switch (mateType) {
            case STUDY -> new StudyLikeQueryStrategy(memberLikesRepository);
            case EXERCISE -> new ExerciseLikeQueryStrategy(memberLikesRepository);
            case MEAL -> new FoodLikeQueryStrategy(memberLikesRepository);
            case ALL -> null;
        };
    }
}
