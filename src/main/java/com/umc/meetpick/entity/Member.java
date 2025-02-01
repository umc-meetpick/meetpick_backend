package com.umc.meetpick.entity;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import lombok.*;

@Getter
@Builder
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
    private University university;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_profile_id")  // ✅ FK 명시적으로 지정
    private MemberProfile memberProfile;

    //외래키
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    @Setter
    private MemberSecondProfile memberSecondProfile;

    // 나이 계산 함수
    public int getAge() {
        // Date를 LocalDate로 변환
        LocalDate birthDate = new java.sql.Date(birthday.getTime()).toLocalDate();
        LocalDate currentDate = LocalDate.now();

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
