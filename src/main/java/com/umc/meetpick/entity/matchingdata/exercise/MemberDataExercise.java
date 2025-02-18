package com.umc.meetpick.entity.matchingdata.exercise;

import com.umc.meetpick.entity.BaseTimeEntity;
import com.umc.meetpick.entity.Major;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.University;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class MemberDataExercise extends BaseTimeEntity {

    // 벡터 계산에 사용되는 데이터
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


    // 운동 관련 정보
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
    private Double isSchool = 0.0;  // 교내/외 운동 여부

    @Enumerated(EnumType.STRING)
    private University university;

    @OneToOne
    private Member member;
}