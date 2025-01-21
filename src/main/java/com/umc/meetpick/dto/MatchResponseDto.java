package com.umc.meetpick.dto;

import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.Hobby;
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

    long memberId;
    int memberNumber;
    long requestId;
    Set<FoodType> foodType;
    Gender gender;
    Hobby hobby;

}
