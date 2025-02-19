package com.umc.meetpick.service.matching.processor;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.exercise.ExerciseMemberDataRepository;
import com.umc.meetpick.repository.exercise.ExerciseMemberRequestDataRepository;
import com.umc.meetpick.repository.food.FoodMemberDataRepository;
import com.umc.meetpick.repository.food.FoodMemberRequestDataRepository;
import com.umc.meetpick.repository.study.StudyMemberDataRepository;
import com.umc.meetpick.repository.study.StudyMemberRequestDataRepository;
import com.umc.meetpick.service.matching.processor.exercise.ExerciseMatchingDataProcessor;
import com.umc.meetpick.service.matching.processor.exercise.ExerciseMemberDataProcessor;
import com.umc.meetpick.service.matching.processor.food.FoodMatchingDataProcessor;
import com.umc.meetpick.service.matching.processor.food.FoodMemberDataProcessor;
import com.umc.meetpick.service.matching.processor.study.StudyMatchingDataProcessor;
import com.umc.meetpick.service.matching.processor.study.StudyMemberDataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDataProcessorFactory {

    private final StudyMemberDataRepository studyMemberDataRepository;
    private final FoodMemberDataRepository foodMemberDataRepository;
    private final ExerciseMemberDataRepository exerciseMemberDataRepository;

    public void getMemberDataProcessor(MemberSecondProfile memberSecondProfile, MateType mateType) {
        switch (mateType) {
            case MEAL -> new FoodMemberDataProcessor(foodMemberDataRepository).process(memberSecondProfile);
            case STUDY -> new StudyMemberDataProcessor(studyMemberDataRepository).process(memberSecondProfile);
            case EXERCISE -> new ExerciseMemberDataProcessor(exerciseMemberDataRepository).process(memberSecondProfile);
        }
    }
}
