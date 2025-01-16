package com.umc.meetpick.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Hobby {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키

    //name
    @Column(nullable = false)
    private String name;

    public Hobby(String name) {
        this.name = name;
    }
}
