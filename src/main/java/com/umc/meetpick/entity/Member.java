package com.umc.meetpick.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.OneToMany;

@Entity
public class Member {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //gender
    @Enumerated(EnumType.STRING)  // enum 값을 문자열로 저장
    @Column(nullable = false)
    private Gender gender;

    //birthday
    @Column(nullable = false)
    private LocalDate birthday;

    //university
    @Column(nullable = false)
    private String university;

    //socialType
    @Enumerated(EnumType.STRING)  // enum 값을 문자열로 저장
    @Column(nullable = false)
    private SocialType socialType;

    @Column(nullable = false)
    private String socialId;

    //status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;


    //role
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    // Member에서 review조회가 필요할 경우
    @OneToMany(mappedBy = "writer")
    private List<Review> writtenReviews = new ArrayList<>();

    @OneToMany(mappedBy = "matchingMember")
    private List<Review> receivedReviews = new ArrayList<>();

    //report 신고 조회가 필요할 경우
    // 작성한 신고들
    @OneToMany(mappedBy = "writer")
    private List<Report> writtenReports = new ArrayList<>();

    //enum
    public enum Gender{
        MALE, FEMALE
    }

    public enum SocialType{
        KAKAO
    }

    public enum MemberRole {
        USER, ADMIN
    }

    public enum MemberStatus {
        ACTIVE, INACTIVE, WITHDRAWN
    }

}
