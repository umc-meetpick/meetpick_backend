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
public class Review extends BaseTimeEntity {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키
    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Member writer; //자기참조

    @ManyToOne
    @JoinColumn(name = "matching_id")
    private Member matchingMember; //자기참조

    //content
    @Column(nullable = false)
    private String content;
}



