package com.umc.meetpick.entity;

import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.MemberRole;
import com.umc.meetpick.enums.MemberStatus;
import com.umc.meetpick.enums.SocialType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private Date birthday;

    @Column(nullable = false)
    private String university;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Column(nullable = false)
    private Long socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberProfile memberProfile;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    public void setMemberProfile(MemberProfile memberProfile) {
        this.memberProfile = memberProfile;
        if (memberProfile != null) {
            memberProfile.setMember(this);
        }
    }
    // ✅ 약관 동의 여부 필드 추가
    @Column(nullable = false)
    private boolean termsAgreed = false;
    @PrePersist
    public void prePersist() {
        if (this.gender == null) this.gender = Gender.MALE;
        if (this.birthday == null) this.birthday = new Date();
        if (this.university == null) this.university = "Unknown University";
        if (this.socialType == null) this.socialType = SocialType.KAKAO;
        if (this.socialId == null) this.socialId = 0L;
        if (this.status == null) this.status = MemberStatus.ACTIVE;
        if (this.role == null) this.role = MemberRole.MEMBER;
    }
}
