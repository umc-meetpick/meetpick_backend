package com.umc.meetpick.service.matching.processor;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.exercise.ExerciseMemberRequestDataRepository;
import com.umc.meetpick.repository.food.FoodMemberRequestDataRepository;
import com.umc.meetpick.repository.study.StudyMemberRequestDataRepository;
import com.umc.meetpick.service.matching.processor.exercise.ExerciseMatchingDataProcessor;
import com.umc.meetpick.service.matching.processor.food.FoodMatchingDataProcessor;
import com.umc.meetpick.service.matching.processor.study.StudyMatchingDataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchingDataDeleteFactory {

    private final StudyMemberRequestDataRepository studyMemberRequestDataRepository;
    private final FoodMemberRequestDataRepository foodMemberRequestDataRepository;
    private final ExerciseMemberRequestDataRepository exerciseMemberRequestDataRepository;

    public void getMatchingDataProcessor(MemberSecondProfile memberSecondProfile, MateType mateType) {
        switch (mateType) {
            case MEAL -> studyMemberRequestDataRepository.deleteByMember(memberSecondProfile.getMember());
            case STUDY -> foodMemberRequestDataRepository.deleteByMember(memberSecondProfile.getMember());
            case EXERCISE -> exerciseMemberRequestDataRepository.deleteByMember(memberSecondProfile.getMember());
        }
    }
}
