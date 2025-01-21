package com.umc.meetpick.dto;

import com.umc.meetpick.enums.FoodType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

import com.umc.meetpick.entity.Request;  // Request 엔티티 import

@Getter
@Builder
public class MatchRequestDto {
    private Long requestId;        // 매칭 요청 ID
    private Long writerId;         // 작성자 ID
    private String writerUniv;     // 작성자 대학교
    private Integer studentNumber; // 학번
    private String mbti;          // MBTI
    private Set<FoodType> food;          // 선호하는 음식
    private String mateType;      // 미팅 타입
    private LocalDateTime createdAt; // 작성 시간

    //RequestEntity->DTO
    public static MatchRequestDto from(Request request) {
        return MatchRequestDto.builder()
                .requestId(request.getId())
                .writerId(request.getWriter().getId())
                .writerUniv(request.getWriter().getUniversity())
                .studentNumber(request.getStudentNumber())
                .mbti(request.getMbti().toString())
                .food(request.getFood())
                .mateType(request.getType().name())
                .createdAt(request.getCreatedAt())
                .build();
    }
}
