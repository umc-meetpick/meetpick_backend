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
import java.util.Set;

@Getter
@Builder
public class MatchRequestDto {
    private Long memberProfileId;        // 매칭 요청 ID
    private Long writerId;         // 작성자 ID
    private String writerUniv;     // 작성자 대학교
    private Integer studentNumber; // 학번
    private MBTI mbti;          // MBTI
    private Set<FoodType> food;          // 선호하는 음식
    private MateType mateType;      // 미팅 타입
    private LocalDateTime createdAt; // 작성 시간

    // TODO 필드 값 수정 필요

    //RequestEntity->DTO
    public static MatchRequestDto from(MemberSecondProfile memberSecondProfile) {

        Member member = memberSecondProfile.getMember();
        MemberProfile memberProfile = member.getMemberProfile();

        return MatchRequestDto.builder()
                .memberProfileId(memberSecondProfile.getId())
                .writerId(member.getId())
                .writerUniv(member.getUniversity().toString())
                .studentNumber(memberProfile.getStudentNumber())
                .mbti(memberProfile.getMBTI())
                .food(memberSecondProfile.getFoodTypes())
                .mateType(memberSecondProfile.getMateType())
                .createdAt(memberSecondProfile.getCreatedAt())
                .build();
    }
}
