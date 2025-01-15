package com.umc.meetpick.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberMapping {
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

    //created_at
    @Column(nullable = false)
    private LocalDateTime createdAt;

    //updatedAt
    @Column(nullable = false)
    private LocalDateTime updatedAt;


}
