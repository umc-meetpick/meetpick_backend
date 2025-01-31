package com.umc.meetpick.dto;

import com.umc.meetpick.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponseDto {

    // TODO 다른 mateType 관련 필드 추가하기
    long memberId;
    long requestId;
    int memberNumber;
    Gender gender;
    Set<FoodType> foodType;
    Set<Hobby> hobby;
    MateType mateType;

}
