package com.umc.meetpick.service.matching.processor;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.exercise.ExerciseMemberDataRepository;
import com.umc.meetpick.repository.food.FoodMemberDataRepository;
import com.umc.meetpick.repository.study.StudyMemberDataRepository;
import com.umc.meetpick.service.matching.processor.exercise.ExerciseMemberDataProcessor;
import com.umc.meetpick.service.matching.processor.food.FoodMemberDataProcessor;
import com.umc.meetpick.service.matching.processor.study.StudyMemberDataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDataDeleteFactory {

    private final StudyMemberDataRepository studyMemberDataRepository;
    private final FoodMemberDataRepository foodMemberDataRepository;
    private final ExerciseMemberDataRepository exerciseMemberDataRepository;

    public void getMemberDataProcessor(MemberSecondProfile memberSecondProfile, MateType mateType) {
        switch (mateType) {
            case MEAL -> studyMemberDataRepository.deleteByMember(memberSecondProfile.getMember());
            case STUDY -> foodMemberDataRepository.deleteByMember(memberSecondProfile.getMember());
            case EXERCISE -> exerciseMemberDataRepository.deleteByMember(memberSecondProfile.getMember());
        }
    }
}
