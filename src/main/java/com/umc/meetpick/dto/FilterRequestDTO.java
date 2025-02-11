package com.umc.meetpick.dto;

import com.umc.meetpick.enums.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FilterRequestDTO {
    // 공통 필터
    private Gender gender;
    private StudentNumber studentNumber;
    private Integer minAge;
    private Integer maxAge;
    private Set<String> availableDays;
    private Set<String> availableTimes;

    // STUDY 필터
    private SubjectType subjectType;
    private CertificateType certificateType;

    // EXERCISE 필터
    private Set<ExerciseType> exerciseTypes;

    // MEAL 필터
    private Set<FoodType> foodTypes;
}
