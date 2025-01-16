package com.umc.meetpick.dto;

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
    String nickname;
    String university;
    String userImage;
}
