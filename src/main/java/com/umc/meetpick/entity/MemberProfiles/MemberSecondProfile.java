package com.umc.meetpick.entity.MemberProfiles;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.Personality;
import com.umc.meetpick.entity.mapping.MemberSecondProfileTimes;
import com.umc.meetpick.enums.*;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class MemberSecondProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "Member")
    private Member member;

    @Enumerated(EnumType.STRING)  // null 시 상관 없음
    @Column(nullable = true)
    private Gender gender;

    @OneToMany
    private List<MemberSecondProfile> memberSecondProfileList = new ArrayList<>();

    @Column(nullable = true)
    private StudentNumber studentNumber;

    @Min(18)
    private int minAge;

    @Max(28)
    private int maxAge;

    @Column(nullable = true)
    private Personality personality;

    @ElementCollection
    private Set<MBTI> mbtis = new HashSet<>();

    boolean isHobbySame;

    @OneToMany
    private List<MemberSecondProfileTimes> memberSecondProfileTimes = new ArrayList<>();

    private int maxPeople;

    private int currentPeople;

    private String comment;

    @ElementCollection
    @Column(nullable = true)
    private Set<ExerciseType> exerciseTypes = new HashSet<>();

    @Column(nullable = true)
    private Boolean isSchool;

    @ElementCollection
    @Column(nullable = true)
    private Set<FoodType> foodTypes = new HashSet<>();

    @Column(nullable = false)
    private MateType mateType;

    // 문자열로 mbti 받고 해당 하는 mbti 리스트 가져오는 함수
    public Set<MBTI> getMbtis(String mbti) {
        // 이후 구현
        return Set.of();
    }

}
