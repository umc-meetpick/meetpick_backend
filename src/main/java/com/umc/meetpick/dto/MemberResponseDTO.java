package com.umc.meetpick.dto;

import com.umc.meetpick.enums.MateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {
    long id;
    String studentNumber;
    String major;
    String nickname;
    String university;
    String userImage;
    String comment;
}
