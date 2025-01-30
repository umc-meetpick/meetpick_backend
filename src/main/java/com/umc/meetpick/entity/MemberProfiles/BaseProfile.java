package com.umc.meetpick.entity.MemberProfiles;

import com.umc.meetpick.entity.Personality;
import com.umc.meetpick.entity.SubMajor;
import com.umc.meetpick.enums.StudentNumber;
import com.umc.meetpick.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class BaseProfile {

    @Enumerated(EnumType.STRING)  // null 시 상관 없음
    @Column(nullable = true)
    private Gender gender;

    @OneToMany
    private Set<SubMajor> subMajors = new HashSet<>();

    @Column(nullable = true)
    private StudentNumber studentNumber;

    private int minAge;

    private int maxAge;

    @Column(nullable = true)
    private Personality personality;

    boolean isHobbySame;



}
