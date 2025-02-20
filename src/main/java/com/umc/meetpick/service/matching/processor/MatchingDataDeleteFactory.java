package com.umc.meetpick.service.matching.processor;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.matchingdata.exercise.MemberRequestDataExercise;
import com.umc.meetpick.entity.matchingdata.food.MemberRequestDataFood;
import com.umc.meetpick.entity.matchingdata.study.MemberRequestDataStudy;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.exercise.ExerciseMemberRequestDataRepository;
import com.umc.meetpick.repository.food.FoodMemberRequestDataRepository;
import com.umc.meetpick.repository.study.StudyMemberRequestDataRepository;
import com.umc.meetpick.service.matching.processor.exercise.ExerciseMatchingDataProcessor;
import com.umc.meetpick.service.matching.processor.food.FoodMatchingDataProcessor;
import com.umc.meetpick.service.matching.processor.study.StudyMatchingDataProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MatchingDataDeleteFactory {

    private final StudyMemberRequestDataRepository studyMemberRequestDataRepository;
    private final FoodMemberRequestDataRepository foodMemberRequestDataRepository;
    private final ExerciseMemberRequestDataRepository exerciseMemberRequestDataRepository;

    public void getMatchingDataProcessor(MemberSecondProfile memberSecondProfile, MateType mateType) {

        log.info("deleteMatchingDataProcessor {}", memberSecondProfile.getId());

        switch (mateType) {
            case MEAL -> {
                MemberRequestDataFood memberRequestDataFood = foodMemberRequestDataRepository.findFirstByMember(memberSecondProfile.getMember());
                memberRequestDataFood.setMember(null); // member를 null로 설정
                foodMemberRequestDataRepository.delete(memberRequestDataFood); // 바로 삭제
                foodMemberRequestDataRepository.flush();
            }
            case STUDY -> {
                MemberRequestDataStudy memberRequestDataStudy = studyMemberRequestDataRepository.findFirstByMember(memberSecondProfile.getMember());
                memberRequestDataStudy.setMember(null);
                studyMemberRequestDataRepository.delete(memberRequestDataStudy);
                studyMemberRequestDataRepository.flush();
            }
            case EXERCISE -> {
                MemberRequestDataExercise memberRequestDataExercise = exerciseMemberRequestDataRepository.findFirstByMember(memberSecondProfile.getMember());
                memberRequestDataExercise.setMember(null);
                exerciseMemberRequestDataRepository.delete(memberRequestDataExercise);
                exerciseMemberRequestDataRepository.flush();
            }
        }
    }
}
