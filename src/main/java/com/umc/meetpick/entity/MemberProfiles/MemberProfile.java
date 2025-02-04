package com.umc.meetpick.entity.MemberProfiles;
import com.umc.meetpick.entity.BaseTimeEntity;
import com.umc.meetpick.entity.Major;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.ContactType;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.Hobby;
import com.umc.meetpick.enums.MBTI;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemberProfile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //추가
    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

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
    @JoinColumn(name = "major_id", nullable = true)
    private Major major;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_profile_hobby",  // 관계 테이블 이름
            joinColumns = @JoinColumn(name = "member_profile_id")  // MemberProfile의 ID를 위한 컬럼
    )
    private Set<Hobby> hobbies;

    //contact
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType contact;

    //contactInfo
    @Column(nullable = false)
    private String contactInfo;

    //추가
    @PrePersist
    public void prePersist() {
        if (this.nickname == null || this.nickname.isEmpty()) this.nickname = "Default Nickname";
        if (this.profileImage == null || this.profileImage.isEmpty()) this.profileImage = "default.png";
        if (this.MBTI == null) this.MBTI = MBTI.INTJ; // 기본 MBTI 설정
        if (this.contact == null) this.contact = ContactType.KAKAO_TALK_ID; // 기본 연락처 타입 설정
        if (this.contactInfo == null || this.contactInfo.isEmpty()) this.contactInfo = "contact@default.com"; // 기본 연락처 정보

    }

}
