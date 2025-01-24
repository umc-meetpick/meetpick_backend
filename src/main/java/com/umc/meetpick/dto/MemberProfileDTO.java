package com.umc.meetpick.dto;

import com.umc.meetpick.enums.ContactType;
import com.umc.meetpick.enums.MBTI;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberProfileDTO {
    private Long memberId;         // 연결된 Member ID
    private String nickname;       // 닉네임
    private String profileImage;   // 프로필 이미지
    private int studentNumber;     // 학번
    private MBTI mbti;             // MBTI
    private ContactType contact;   // 연락처 타입 (이메일, 전화 등)
    private String contactInfo;    // 연락처 정보
}
