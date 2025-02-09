package com.umc.meetpick.dto;

import com.umc.meetpick.enums.Week;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSecondProfileTimesDTO {
    private String week;
    private Set<Integer> times;
}
