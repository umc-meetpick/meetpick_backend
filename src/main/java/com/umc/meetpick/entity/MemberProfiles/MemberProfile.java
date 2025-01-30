package com.umc.meetpick.entity.MemberProfiles;
import com.umc.meetpick.entity.BaseTimeEntity;
import com.umc.meetpick.entity.Major;
import com.umc.meetpick.enums.ContactType;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.Hobby;
import com.umc.meetpick.enums.MBTI;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemberProfile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 닉네임
    @Column(nullable = false)
    private String nickname;

    // 프로필 이미지
    @Column(nullable = false)
    private String profileImage;

    // 학번
    @Column(nullable = false)
    private int studentNumber;

    //MBTI
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MBTI MBTI;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Major major;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<Hobby> hobbies;

    //contact
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType contact;

    //contactInfo
    @Column(nullable = false)
    private String contactInfo;
}
