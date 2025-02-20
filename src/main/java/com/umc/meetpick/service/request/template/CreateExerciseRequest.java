package com.umc.meetpick.service.request.template;

import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.ExerciseType;
import com.umc.meetpick.enums.FoodType;

import java.util.HashSet;
import java.util.stream.Collectors;

public class CreateExerciseRequest extends CreateRequest {

    @Override
    protected MemberSecondProfile process(RequestDTO.NewRequestDTO newRequest, MemberSecondProfile tempMemberSecondProfile) {

        tempMemberSecondProfile.setExerciseType(newRequest.getExerciseTypes() == null ? ExerciseType.OTHER : ExerciseType.fromString(newRequest.getExerciseTypes())); // 운동 정보 저장
        tempMemberSecondProfile.setIsSchool(newRequest.getIsSchool() != null && newRequest.getIsSchool());
        tempMemberSecondProfile.setPlace(newRequest.getPlace() == null ? "기타" : newRequest.getPlace());

        return tempMemberSecondProfile;
    }
}
