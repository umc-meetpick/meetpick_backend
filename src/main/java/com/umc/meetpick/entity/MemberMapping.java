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
