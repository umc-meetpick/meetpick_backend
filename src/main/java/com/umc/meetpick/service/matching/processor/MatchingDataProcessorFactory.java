package com.umc.meetpick.service.matching.processor;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.exercise.ExerciseMemberRequestDataRepository;
import com.umc.meetpick.repository.food.FoodMemberRequestDataRepository;
import com.umc.meetpick.repository.study.StudyMemberDataRepository;
import com.umc.meetpick.repository.study.StudyMemberRequestDataRepository;
import com.umc.meetpick.service.matching.processor.exercise.ExerciseMatchingDataProcessor;
import com.umc.meetpick.service.matching.processor.food.FoodMatchingDataProcessor;
import com.umc.meetpick.service.matching.processor.food.FoodMemberDataProcessor;
import com.umc.meetpick.service.matching.processor.study.StudyMatchingDataProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MatchingDataProcessorFactory {

    private final StudyMemberRequestDataRepository studyMemberRequestDataRepository;
    private final FoodMemberRequestDataRepository foodMemberRequestDataRepository;
    private final ExerciseMemberRequestDataRepository exerciseMemberRequestDataRepository;

    public void getMatchingDataProcessor(MemberSecondProfile memberSecondProfile, MateType mateType) {

        log.info("getMatchingDataProcessor {}", memberSecondProfile.getId());

        switch (mateType) {
            case MEAL -> new FoodMatchingDataProcessor(foodMemberRequestDataRepository).process(memberSecondProfile);
            case STUDY -> new StudyMatchingDataProcessor(studyMemberRequestDataRepository).process(memberSecondProfile);
            case EXERCISE -> new ExerciseMatchingDataProcessor(exerciseMemberRequestDataRepository).process(memberSecondProfile);
        }
    }
}
