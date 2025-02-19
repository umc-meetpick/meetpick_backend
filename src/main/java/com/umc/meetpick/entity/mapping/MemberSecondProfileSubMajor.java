package com.umc.meetpick.entity.mapping;

import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.SubMajor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class MemberSecondProfileSubMajor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_second_profile_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberSecondProfile memberSecondProfile;

    @ManyToOne
    @JoinColumn
    private SubMajor subMajor;

}
