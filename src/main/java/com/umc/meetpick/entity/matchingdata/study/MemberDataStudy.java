package com.umc.meetpick.entity.matchingdata.study;


import com.umc.meetpick.entity.BaseTimeEntity;
import com.umc.meetpick.entity.Major;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.University;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

// 멤버의 정보를 저장함
@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class MemberDataStudy {

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


    // 스터디 유형 관련
    @Builder.Default
    private Double major = 0.0;         // 전공

    @Builder.Default
    private Double nonMajor = 0.0;      // 교양

    @Builder.Default
    private Double study = 0.0;         // 스터디


    // 자격증 유형 관련
    @Builder.Default
    private Double language = 0.0;           // 어학

    @Builder.Default
    private Double employment = 0.0;         // 취업

    @Builder.Default
    private Double civilService = 0.0;       // 고시/공무원

    @Builder.Default
    private Double socialScience = 0.0;      // 사회과학계열

    @Builder.Default
    private Double hobby = 0.0;              // 취미/교양

    @Builder.Default
    private Double programming = 0.0;        // 프로그래밍

    // 온라인/오프라인 선호도
    @Builder.Default
    private Double isOnline = 0.0;


    @Enumerated(EnumType.STRING)
    private University university;

    @OneToOne
    private Member member;
}
