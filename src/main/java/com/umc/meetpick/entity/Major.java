package com.umc.meetpick.entity;
import jakarta.persistence.*;

@Entity
public class Major {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키

    @Column(nullable = false)
    private String name;

}
