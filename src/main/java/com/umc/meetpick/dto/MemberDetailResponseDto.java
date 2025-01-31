package com.umc.meetpick.dto;

import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.Hobby;
import com.umc.meetpick.enums.MBTI;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailResponseDto {
    long userId;
    int age;
    int studentNumber;
    String gender;
    MBTI mbti;
    String major;
    Set<String> hobbies;
    Set<String> foodTypes;
    Set<Map<String, String>> date;
    String comment;
}
