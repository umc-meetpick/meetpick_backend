package com.umc.meetpick.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notification {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne //1:1 mapping_id 양방향
    @JoinColumn(name = "mapping_id")
    private MemberMapping memberMapping;



    //type
    @Enumerated(EnumType.STRING)  // enum 값을 문자열로 저장
    @Column(nullable = false)
    private NotificationType type;

    //content
    @Column(nullable = false)
    private String content;

    //is_read
    @Column(nullable = false)
    private Boolean is_read;

    //created_at
    @Column(nullable = false)
    private LocalDateTime createdAt;


    public enum NotificationType{
        알림1,
        알림2
    }
}
