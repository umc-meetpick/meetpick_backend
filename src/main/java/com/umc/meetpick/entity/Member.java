package com.umc.meetpick.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import java.time.LocalDate;

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
