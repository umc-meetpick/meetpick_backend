package com.umc.meetpick.dto;

import com.umc.meetpick.entity.Hobby;
import com.umc.meetpick.entity.Major;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.MBTI;
import com.umc.meetpick.enums.MateType;
import lombok.*;


public class RequestDTO {

    // 새로운 매칭 요청 api 관련 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewRequestDTO {
        private Long writerId;
        //private String majorName;
        private String subMajorName;
        private String hobbyName;
        private Integer studentNumber;
        private MBTI mbti;
        private Integer minAge;
        private Integer maxAge;
        private Integer minTime;
        private Integer maxTime;
        private FoodType food;
        private Integer maxPeople;
        private MateType type;
    }

    // 매칭에 참여하기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinRequestDTO {
        private Long requesterId;
        private Long postUserId;
    }

}
