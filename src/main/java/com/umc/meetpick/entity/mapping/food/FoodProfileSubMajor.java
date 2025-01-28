package com.umc.meetpick.entity.mapping.food;

import com.umc.meetpick.entity.MemberProfiles.FoodProfile;
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
public class FoodProfileSubMajor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private FoodProfile foodProfile;

    @ManyToOne
    @JoinColumn
    private SubMajor subMajor;

}
