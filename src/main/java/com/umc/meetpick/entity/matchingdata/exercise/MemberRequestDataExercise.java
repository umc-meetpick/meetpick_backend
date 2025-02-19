package com.umc.meetpick.entity.matchingdata.exercise;

import com.umc.meetpick.entity.BaseTimeEntity;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.University;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class MemberRequestDataExercise extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기본 정보 (공통) - 매칭을 원하는 상대방의 조건
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


    //학과

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


    // 운동 관련 정보 - 원하는 운동 조건
    @Builder.Default
    private Double bowling = 0.0;

    @Builder.Default
    private Double climbing = 0.0;

    @Builder.Default
    private Double tableTennis = 0.0;

    @Builder.Default
    private Double fitness = 0.0;

    @Builder.Default
    private Double running = 0.0;

    @Builder.Default
    private Double soccer = 0.0;

    @Builder.Default
    private Double basketball = 0.0;

    @Builder.Default
    private Double tennisBadminton = 0.0;

    @Builder.Default
    private Double isSchool = 0.0;

    @Enumerated(EnumType.STRING)
    private University university;

    @OneToOne
    private Member member;
}
