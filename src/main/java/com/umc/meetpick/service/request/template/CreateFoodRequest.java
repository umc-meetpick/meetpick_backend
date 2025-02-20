package com.umc.meetpick.service.request.template;

import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.FoodType;

import java.util.HashSet;
import java.util.stream.Collectors;

public class CreateFoodRequest extends CreateRequest {

    @Override
    protected MemberSecondProfile process(RequestDTO.NewRequestDTO newRequest, MemberSecondProfile tempMemberSecondProfile) {

        tempMemberSecondProfile.setFoodTypes(newRequest.getFood().isEmpty() ? new HashSet<>() : newRequest.getFood().stream().map(FoodType::fromString).collect(Collectors.toSet())); // 음식 정보 저장

        return tempMemberSecondProfile;
    }
}
