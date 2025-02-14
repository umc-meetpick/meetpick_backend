package com.umc.meetpick.entity.mapping;
import com.umc.meetpick.entity.BaseTimeEntity;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class MemberSecondProfileLikes extends BaseTimeEntity {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키 사용자,공고
    @ManyToOne //N:1 emberprofile_id
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne //N:1 request_id
    @JoinColumn
    private MemberSecondProfile memberSecondProfile;

}
