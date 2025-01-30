package com.umc.meetpick.entity;

import com.umc.meetpick.enums.ContactType;
import com.umc.meetpick.enums.Hobby;
import com.umc.meetpick.enums.MBTI;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

//    @ManyToOne
//    @JoinColumn(name = "hobby_id")
//    private Hobby hobby;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = false)
    private int studentNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MBTI MBTI;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType contact;

    @Column(nullable = false)
    private String contactInfo;

    @PrePersist
    public void prePersist() {
        if (this.nickname == null || this.nickname.isEmpty()) this.nickname = "Default Nickname";
        if (this.profileImage == null || this.profileImage.isEmpty()) this.profileImage = "default.png";
        if (this.MBTI == null) this.MBTI = MBTI.INTJ; // 기본 MBTI 설정
        if (this.contact == null) this.contact = ContactType.KAKAO_TALK_ID; // 기본 연락처 타입 설정
        if (this.contactInfo == null || this.contactInfo.isEmpty()) this.contactInfo = "contact@default.com"; // 기본 연락처 정보
    }
}
