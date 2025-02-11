package com.umc.meetpick.dto;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.MBTI;
import com.umc.meetpick.enums.MateType;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchRequestDto {

    private Long mappingId;  // 매칭 요청 ID
    private Long memberSecondProfileId;         // 작성자 ID
    private String studentNumber; // 학번
    private String major;          // 전공
    private Integer age;           // 나이
    private String mateType;       // 미팅 타입
    private String createdAt;      // yyyy-MM-dd 형식 날짜

}

