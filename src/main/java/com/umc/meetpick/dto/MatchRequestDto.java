package com.umc.meetpick.dto;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.MBTI;
import com.umc.meetpick.enums.MateType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Getter
@Builder
public class MatchRequestDto {
    private Long memberProfileId;  // 매칭 요청 ID
    private Long writerId;         // 작성자 ID
    private Integer studentNumber; // 학번
    private String major;          // 전공
    private Integer age;           // 나이
    private String mateType;       // 미팅 타입
    private String createdAt;      // yyyy-MM-dd 형식 날짜

    public static MatchRequestDto from(MemberSecondProfile memberSecondProfile) {
        Member member = memberSecondProfile.getMember();
        MemberProfile memberProfile = member.getMemberProfile();

        // 날짜 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = memberSecondProfile.getCreatedAt().format(formatter);

        return MatchRequestDto.builder()
                .memberProfileId(memberSecondProfile.getId())
                .writerId(member.getId())
                .studentNumber(memberProfile.getStudentNumber())
                .major(memberProfile.getSubMajor().getName())
                .age(member.getAge())
                .mateType(memberSecondProfile.getMateType().getKoreanName())
                .createdAt(formattedDate)
                .build();
    }
}

