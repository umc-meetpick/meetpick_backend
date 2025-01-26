package com.umc.meetpick.dto;

import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.MBTI;
import com.umc.meetpick.enums.MateType;
import lombok.*;

import java.util.Set;


public class RequestDTO {

    // 새로운 매칭 요청 api 관련 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewRequestDTO {
        private Long writerId;
        //private String majorName;
        private Set<String> subMajorName;
        private String isHobbySame;
        private Integer studentNumber;
        private Set<MBTI> mbti;
        private Integer minAge;
        private Integer maxAge;
        private Integer minTime;
        private Integer maxTime;
        private Set<FoodType> food;
        private Integer maxPeople;
        private MateType type;
    }

    // 매칭에 참여하기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinRequestDTO {
        private Long requestId;
        private Long postUserId;
        private Boolean status;
    }

    // 매칭에 좋아요 누르기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeRequestDTO {
        private Long requestId;
        private Long postUserId;
    }

    // 매칭 승낙 or 거절
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class isAcceptedDTO{
        private Boolean isAccepted;
        private Long requestId;
        private Boolean status;
    }

}
