package com.umc.meetpick.dto;

//import com.umc.meetpick.entity.Personality;
import com.umc.meetpick.entity.mapping.MemberSecondProfileSubMajor;
import com.umc.meetpick.entity.mapping.MemberSecondProfileTimes;
import com.umc.meetpick.enums.*;
import lombok.*;

import java.util.List;
import java.util.Set;


public class RequestDTO {

    // 새로운 매칭 요청 api 관련 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewRequestDTO {
        //private Long writerId;
        //private String majorName;
        private Gender gender;
        private List<String> subMajorName;
        private String studentNumber;
        private int minAge;
        private int maxAge;
        //private List<String> personality;
        //private Set<MBTI> mbti;
        private String mbti;
        private Boolean isHobbySame;
        private List<MemberSecondProfileTimesDTO> memberSecondProfileTimes;
        private int maxPeople;
        private String comment;
        private String exerciseTypes;
        private Boolean isSchool;
        private List<String> food;
        private String studyType;
        private String majorNameAndProfessorName;
        private String isOnline;
        private int studyTimes;
        private String place;
        private MateType type;
    }

    // 매칭에 참여하기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinRequestDTO {
        private Long requestId;
        //private Long postUserId;
        //private Boolean status;
    }

    // 매칭에 좋아요 누르기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeRequestDTO {
        private Long requestId;
        //private Long postUserId;
    }

    // 매칭 승낙 or 거절
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class isAcceptedDTO{
        private Boolean isAccepted;
        private Long matchingRequestId;
        private Boolean status;
    }
}
