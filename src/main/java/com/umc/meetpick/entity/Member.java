package com.umc.meetpick.entity;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Member {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이름
    @Column(nullable = false)
    private String name;

    //gender
    @Enumerated(EnumType.STRING)  // enum 값을 문자열로 저장
    @Column(nullable = false)
    private Gender gender;

    //birthday
    @Column(nullable = false)
    private Date birthday;

    //university
    @Enumerated(EnumType.STRING)  // enum 값을 문자열로 저장
    @Column(nullable = false)
    private University university;

    //socialType
    @Enumerated(EnumType.STRING)  // enum 값을 문자열로 저장
    @Column(nullable = false)
    private SocialType socialType;

    @Column(nullable = false)
    private Long socialId;

    //status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    //role
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @Column(nullable = false)
    private boolean isVerified = false;

    @OneToOne
    @Setter
    @JoinColumn(name = "member_profile_id")
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

        // 현재 날짜와 생일을 기준으로 나이 계산
        return Period.between(birthDate, currentDate).getYears();
    }

    public void setMember(String name, Gender gender, Date birthday){
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }


    //양방향 매핑 설정

 // Member에서 review조회가 필요할 경우
    @OneToMany(mappedBy = "writer")
    private List<Review> writtenReviews = new ArrayList<>();

    @OneToMany(mappedBy = "matchingMember")
    private List<Review> receivedReviews = new ArrayList<>();
  /*
    //report 신고 조회가 필요할 경우
    // 작성한 신고들
    @OneToMany(mappedBy = "writer")
    private List<Report> writtenReports = new ArrayList<>();*/

    @PrePersist
    public void prePersist() {
        if (this.name == null || this.name.trim().isEmpty()) this.name = "Unknown User";
        if (this.gender == null) this.gender = Gender.MALE;
        if (this.birthday == null) this.birthday = new Date();
        if (this.university == null) this.university = University.SEOUL_NATIONAL_UNIVERSITY;
        if (this.socialType == null) this.socialType = SocialType.KAKAO;
        if (this.socialId == null) this.socialId = 0L;
        if (this.status == null) this.status = MemberStatus.ACTIVE;
        if (this.role == null) this.role = MemberRole.MEMBER;
    }


    public void setUniversity(University universityEnum) {
        this.university = universityEnum;
        this.isVerified = true;
    }
}
