package com.umc.meetpick.service.matching.processor;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.matchingdata.exercise.MemberDataExercise;
import com.umc.meetpick.entity.matchingdata.food.MemberDataFood;
import com.umc.meetpick.entity.matchingdata.study.MemberDataStudy;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.exercise.ExerciseMemberDataRepository;
import com.umc.meetpick.repository.food.FoodMemberDataRepository;
import com.umc.meetpick.repository.study.StudyMemberDataRepository;
import com.umc.meetpick.service.matching.processor.exercise.ExerciseMemberDataProcessor;
import com.umc.meetpick.service.matching.processor.food.FoodMemberDataProcessor;
import com.umc.meetpick.service.matching.processor.study.StudyMemberDataProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberDataDeleteFactory {

    private final StudyMemberDataRepository studyMemberDataRepository;
    private final FoodMemberDataRepository foodMemberDataRepository;
    private final ExerciseMemberDataRepository exerciseMemberDataRepository;

    public void getMemberDataProcessor(MemberSecondProfile memberSecondProfile, MateType mateType) {

        log.info("deleteMemberDataProcessor {}", memberSecondProfile.getId());

        switch (mateType) {
            case MEAL -> {
                MemberDataFood memberDataFood = foodMemberDataRepository.findFirstByMember(memberSecondProfile.getMember());
                memberDataFood.setMember(null);
                foodMemberDataRepository.delete(memberDataFood);
                foodMemberDataRepository.flush(); //TODO
            }
            case STUDY -> {
                MemberDataStudy memberDataStudy = studyMemberDataRepository.findFirstByMember(memberSecondProfile.getMember());
                memberDataStudy.setMember(null);
                studyMemberDataRepository.delete(memberDataStudy);
                studyMemberDataRepository.flush();
            }
            case EXERCISE -> {
                MemberDataExercise memberDataExercise = exerciseMemberDataRepository.findFirstByMember(memberSecondProfile.getMember());
                memberDataExercise.setMember(null);
                exerciseMemberDataRepository.delete(memberDataExercise);
                exerciseMemberDataRepository.flush();
            }
        }
    }
}
