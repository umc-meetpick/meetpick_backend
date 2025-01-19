package com.umc.meetpick.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@Entity(name = "university")
@AllArgsConstructor
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String universityName;

    @Column(nullable = false)
    private String address;

    public University(String name, String address) {
        this.universityName = name;
        this.address = address;
    }
}
