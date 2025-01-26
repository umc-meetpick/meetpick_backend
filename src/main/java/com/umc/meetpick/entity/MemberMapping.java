package com.umc.meetpick.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Setter
public class MemberMapping extends BaseTimeEntity {
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
    @JoinColumn(name = "request_id")
    private Request request;

    //status - 매칭 완료 여부 - 거절 or 승낙시 true로
    @Column(nullable = false)
    private Boolean status;

    // 매칭 승낙 여부
    @Column
    private Boolean isAccepted;
}
