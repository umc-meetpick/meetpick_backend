package com.umc.meetpick.entity.MemberProfiles;

import com.umc.meetpick.entity.Personality;
import com.umc.meetpick.entity.mapping.food.FoodProfileSubMajor;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.MBTI;
import com.umc.meetpick.enums.StudentNumber;
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
public class StudyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)  // null 시 상관 없음
    @Column(nullable = true)
    private Gender gender;

    @OneToMany
    private List<FoodProfileSubMajor> foodProfileSubMajorList = new ArrayList<>();

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

    @ElementCollection
    private Set<Integer> times = new HashSet<>();

    @ElementCollection
    private Set<FoodType> foodTypes = new HashSet<>();

    private int maxPeople;

    private int currentPeople;

    private String comment;

    // 문자열로 mbti 받고 해당 하는 mbti 리스트 가져오는 함수
    public Set<MBTI> getMbtis(String mbti) {
        // 이후 구현
        return Set.of();
    }

}
