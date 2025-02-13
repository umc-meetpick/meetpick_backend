package com.umc.meetpick.entity.MemberProfiles;

import com.umc.meetpick.entity.BaseTimeEntity;
import com.umc.meetpick.entity.Member;
//import com.umc.meetpick.entity.Personality;
import com.umc.meetpick.entity.mapping.MemberSecondProfileSubMajor;
import com.umc.meetpick.entity.mapping.MemberSecondProfileTimes;
import com.umc.meetpick.enums.*;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class MemberSecondProfile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Member와의 관계 (N:1)
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)  // null 시 상관 없음
    @Column(nullable = true)
    private Gender gender;

    @OneToMany(mappedBy = "memberSecondProfile", cascade = CascadeType.ALL)
    private List<MemberSecondProfileSubMajor> memberSecondProfileMajorList;

    @Column(nullable = true)
    private StudentNumber studentNumber;

    @Min(18)
    @Max(28)
    @Column(nullable = true)
    private Integer minAge;

    @Min(18)
    @Max(28)
    @Column(nullable = true)
    private Integer maxAge;

//    @OneToOne
//    @JoinColumn
//    private Personality personality;

    //@ElementCollection
    //@Builder.Default
    //private Set<MBTI> mbti = new HashSet<>();
    private String mbti;

    private Boolean isHobbySame;

    @Builder.Default
    @OneToMany
    private List<MemberSecondProfileTimes> memberSecondProfileTimes = new ArrayList<>();

    private int maxPeople;

    private int currentPeople;

    private String comment;

    // 운동 관련

    @Enumerated(EnumType.STRING)  // null 시 상관 없음
    @Column(nullable = true)
    private ExerciseType exerciseType;

    @Column(nullable = true)
    private Boolean isSchool;

    // 음식 관련

    @ElementCollection
    @Column(nullable = true)
    @Builder.Default
    private Set<FoodType> foodTypes = new HashSet<>();

    // 공부 관련
    @Enumerated(EnumType.STRING)  // null 시 상관 없음
    @Column(nullable = true)
    private StudyType studyType;

    private String majorName;

    private String professorName;

    // 온라인인지 - 같이도 그냥 온라인 (매칭 시 같이면 둘다 ㄱㅊ다는 뜻이니깐...)
    private Boolean isOnline;

    // 몇번인지
    private int studyTimes;

    // 공부 or 운동 장소
    private String place;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MateType mateType;

    // 인원 수 초과 방지
    public void addPerson() {
        if (currentPeople + 1 > maxPeople) {
            throw new IllegalArgumentException("현재 인원이 최대 인원을 초과할 수 없습니다.");
        }
        currentPeople++;
    }

}
