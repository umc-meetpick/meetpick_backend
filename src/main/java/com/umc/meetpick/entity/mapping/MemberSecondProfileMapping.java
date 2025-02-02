package com.umc.meetpick.entity.mapping;
import com.umc.meetpick.entity.BaseTimeEntity;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.Notification;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Setter
public class MemberSecondProfileMapping extends BaseTimeEntity {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키
    @ManyToOne //N:1 member_id
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne //1:1 notification_id 양방향
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @ManyToOne //N:1 request_id
    @JoinColumn
    private MemberSecondProfile memberSecondProfile;

    //status - 매칭 완료 여부 - 거절 or 승낙시 true로
    @Column(nullable = false)
    private Boolean status;

    // 매칭 승낙 여부
    @Column
    private Boolean isAccepted;
}
