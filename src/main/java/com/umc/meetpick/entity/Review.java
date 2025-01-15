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

    //content
    @Column(nullable = false)
    private String content;

    //created_at
    @Column(nullable = false)
    private LocalDateTime createdAt;




}
