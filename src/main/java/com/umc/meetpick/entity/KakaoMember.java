package com.umc.meetpick.entity;

import com.umc.meetpick.enums.SocialType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "kakao_member")
public class KakaoMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long socialId;  // 카카오 고유 ID

    private String nickname;
    private String gender;
    private String birthday;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    // ✅ KakaoMember와 Member 간 1:1 관계 설정
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")  // FK 설정
    private Member member;

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
