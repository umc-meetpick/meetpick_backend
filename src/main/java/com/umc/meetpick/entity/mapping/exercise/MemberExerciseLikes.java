package com.umc.meetpick.entity.mapping.exercise;
import com.umc.meetpick.entity.BaseTimeEntity;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.ExerciseProfile;
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
public class MemberExerciseLikes extends BaseTimeEntity {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키 사용자,공고
    @ManyToOne //N:1 emberprofile_id
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne //N:1 request_id
    @JoinColumn
    private ExerciseProfile exerciseProfile;

}
