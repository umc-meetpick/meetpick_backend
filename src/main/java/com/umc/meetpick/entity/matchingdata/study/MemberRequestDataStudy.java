package com.umc.meetpick.entity.matchingdata.study;

import com.umc.meetpick.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.University;
import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class MemberRequestDataStudy extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기본 정보 (공통)
    @Builder.Default
    private Double gender = 0.0;

    @Builder.Default
    private Double age = 0.0;

    @Builder.Default
    private Double IE = 0.0;

    @Builder.Default
    private Double SN = 0.0;

    @Builder.Default
    private Double TF = 0.0;

    @Builder.Default
    private Double JP = 0.0;

    // 학과
    @Builder.Default
    private Double engineering = 0.0;

    @Builder.Default
    private Double science = 0.0;

    @Builder.Default
    private Double humanities = 0.0;

    @Builder.Default
    private Double socialScience = 0.0;

    @Builder.Default
    private Double medicine = 0.0;

    @Builder.Default
    private Double artsAndPhysical = 0.0;

    @Builder.Default
    private Double agricultureAndLife = 0.0;

    @Builder.Default
    private Double convergenceAndSpecialization = 0.0;

    // 스터디 유형 관련
    @Builder.Default
    private Double major = 0.0;         // 전공

    @Builder.Default
    private Double nonMajor = 0.0;      // 교양

    @Builder.Default
    private Double study = 0.0;         // 스터디

    @Builder.Default
    private Double isOnline = 0.0;      // 온라인,오프라인

    @Builder.Default
    private Double studyTimes = 0.0;    // 횟수

    @Enumerated(EnumType.STRING)
    private University university;

    @OneToOne
    private Member member;
}
