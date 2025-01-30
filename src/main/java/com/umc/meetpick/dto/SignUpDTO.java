package com.umc.meetpick.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter  // 추가된 부분: memberId를 설정할 수 있도록 Setter 추가
@Builder
public class SignUpDTO {
    private Long memberId;
    private String name;
    private String gender;
    private String birthday;
}
