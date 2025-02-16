package com.umc.meetpick.entity.matchingdata.food;

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
public class MemberData extends BaseTimeEntity {

    // 벡터 계산에 사용되는 데이터

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Builder.Default
    private Double KOREAN = 0.0;

    @Builder.Default
    private Double WESTERN = 0.0;

    @Builder.Default
    private Double JAPANESE = 0.0;

    @Builder.Default
    private Double CHINESE = 0.0;

    @Builder.Default
    private Double VIETNAMESE = 0.0;

    // 핕러링의 위한 필드
    @Enumerated(EnumType.STRING)  // enum 값을 문자열로 저장
    private University university;

    @OneToOne
    private Member member;

}
