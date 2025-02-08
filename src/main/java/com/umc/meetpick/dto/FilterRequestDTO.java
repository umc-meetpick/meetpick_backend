package com.umc.meetpick.dto;

import com.umc.meetpick.enums.ExerciseType;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.StudentNumber;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FilterRequestDTO {
    private Gender gender;
    private StudentNumber studentNumber;
    private List<ExerciseType> exerciseTypes;
    private List<FoodType> foodTypes;
    private Boolean isSchool;
}
