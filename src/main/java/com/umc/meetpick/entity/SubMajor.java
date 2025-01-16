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
public class SubMajor {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //name
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "sub_major")
    private Major Major;

    public SubMajor(String name,Major Major) {
        this.name = name;
        this.Major = Major;
    }
}
