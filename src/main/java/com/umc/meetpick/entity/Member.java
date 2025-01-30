package com.umc.meetpick.entity;
import com.umc.meetpick.entity.MemberProfiles.FoodProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.enums.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private LocalDate birthday;

    // 학번
    @Column(nullable = false)
    private int studentNumber;

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

    @OneToOne
    @JoinColumn(name = "member_profile")
    private MemberProfile memberProfile;

    //외래키
    @OneToOne
    @JoinColumn(name = "food_profile")
    private FoodProfile foodProfile;

    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthday, currentDate).getYears();
    }

    //양방향 매핑 설정

  /*  // Member에서 review조회가 필요할 경우
    @OneToMany(mappedBy = "writer")
    private List<Review> writtenReviews = new ArrayList<>();

    @OneToMany(mappedBy = "matchingMember")
    private List<Review> receivedReviews = new ArrayList<>();

    //report 신고 조회가 필요할 경우
    // 작성한 신고들
    @OneToMany(mappedBy = "writer")
    private List<Report> writtenReports = new ArrayList<>();*/


}
