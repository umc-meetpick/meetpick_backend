package com.umc.meetpick.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Report {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키

    // 신고 작성자
    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Member writer;

    // 신고 대상자
    @ManyToOne
    @JoinColumn(name = "matching_id")
    private Member matchingMember;


    //content
    @Column(nullable = false)
    private String content;

    //created_at
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
