package com.umc.meetpick.dto;

import com.umc.meetpick.enums.MateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmResponseDto {
    String mateType;
    String content;
    String createdAt;
    Long mappingId;
}
