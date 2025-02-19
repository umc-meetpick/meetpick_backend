package com.umc.meetpick.entity.mapping;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.Week;
import jakarta.persistence.*;
import jakarta.validation.constraints.Negative;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemberSecondProfileTimes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Week week;

    @OnDelete(action = OnDeleteAction.CASCADE) // Hibernate 전용 애너테이션
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "member_second_profile_times_values",
            joinColumns = @JoinColumn(name = "member_second_profile_times_id", nullable = false)
    )
    @Builder.Default
    private Set<Integer> times = new HashSet<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    private MemberSecondProfile memberSecondProfile;

}
