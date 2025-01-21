package com.umc.meetpick.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
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

    //status
    @Column(nullable = false)
    private Boolean status;
}
