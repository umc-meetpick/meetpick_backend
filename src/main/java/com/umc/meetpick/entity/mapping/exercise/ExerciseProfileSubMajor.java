package com.umc.meetpick.entity.mapping.exercise;

import com.umc.meetpick.entity.MemberProfiles.ExerciseProfile;
import com.umc.meetpick.entity.SubMajor;
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
public class ExerciseProfileSubMajor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private ExerciseProfile exerciseProfile;

    @ManyToOne
    @JoinColumn
    private SubMajor subMajor;

}
