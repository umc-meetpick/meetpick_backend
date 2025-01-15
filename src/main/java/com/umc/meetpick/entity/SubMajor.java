package com.umc.meetpick.entity;
import jakarta.persistence.*;

@Entity
public class SubMajor {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키

    //name
    @Column(nullable = false)
    private String name;
}
