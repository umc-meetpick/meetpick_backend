package com.umc.meetpick.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Review {
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

    //created_at
    @Column(nullable = false)
    private LocalDateTime createdAt;




}
