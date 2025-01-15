package com.umc.meetpick.entity;
import jakarta.persistence.*;

@Entity
public class Hobby extends BaseTimeEntity {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키

    //name
    @Column(nullable = false)
    private String name;
}
