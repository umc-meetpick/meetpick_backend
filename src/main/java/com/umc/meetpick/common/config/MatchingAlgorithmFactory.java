package com.umc.meetpick.common.config;


import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.service.matching.algorithm.ExerciseContentsFiltering;
import com.umc.meetpick.service.matching.algorithm.FoodContentsFiltering;
import com.umc.meetpick.service.matching.algorithm.MatchingAlgorithm;
import com.umc.meetpick.service.matching.algorithm.StudyContentsFiltering;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MatchingAlgorithmFactory {

    private final FoodContentsFiltering foodContentsFiltering;
    private final ExerciseContentsFiltering exerciseContentsFiltering;
    private final StudyContentsFiltering studyContentsFiltering;

    public MatchingAlgorithmFactory(FoodContentsFiltering foodContentsFiltering,
                                    ExerciseContentsFiltering exerciseContentsFiltering,
                                    StudyContentsFiltering studyContentsFiltering) {
        this.foodContentsFiltering = foodContentsFiltering;
        this.exerciseContentsFiltering = exerciseContentsFiltering;
        this.studyContentsFiltering = studyContentsFiltering;
    }

    @Bean // Map을 Bean으로 등록하여 자동 주입
    public Map<MateType, MatchingAlgorithm<?>> matchingAlgorithms() {
        Map<MateType, MatchingAlgorithm<?>> map = new HashMap<>();
        map.put(MateType.MEAL, foodContentsFiltering);
        map.put(MateType.EXERCISE, exerciseContentsFiltering);
        map.put(MateType.STUDY, studyContentsFiltering);
        return map;
    }
}
