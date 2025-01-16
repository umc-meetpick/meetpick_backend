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
public class Report extends BaseTimeEntity {
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
}
