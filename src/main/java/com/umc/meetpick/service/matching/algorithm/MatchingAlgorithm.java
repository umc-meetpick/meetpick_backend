package com.umc.meetpick.service.matching.algorithm;

import com.umc.meetpick.dto.RecommendDto;
import com.umc.meetpick.entity.Member;

public interface MatchingAlgorithm<T> {

    T recommend(Member member);

    //RecommendDto.RecommendPageDto recommend(Member member);
    //public <T> T recommend(Member member);
    //public RecommendDto.FoodRecommendPageDto recommend(Member member);
}

