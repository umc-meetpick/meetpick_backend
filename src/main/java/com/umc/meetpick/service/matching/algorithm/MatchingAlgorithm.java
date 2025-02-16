package com.umc.meetpick.service.matching.algorithm;

import com.umc.meetpick.dto.RecommendDto;
import com.umc.meetpick.entity.Member;

public interface MatchingAlgorithm {
    public RecommendDto.FoodRecommendPageDto recommend(Member member);
}
