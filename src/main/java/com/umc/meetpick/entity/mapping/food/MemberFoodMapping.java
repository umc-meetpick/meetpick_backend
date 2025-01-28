package com.umc.meetpick.entity.mapping.food;
import com.umc.meetpick.entity.BaseTimeEntity;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.ExerciseProfile;
import com.umc.meetpick.entity.MemberProfiles.FoodProfile;
import com.umc.meetpick.entity.Notification;
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
public class MemberFoodMapping extends BaseTimeEntity {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키
    @ManyToOne //N:1 member_id
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne //1:1 notification_id 양방향
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @ManyToOne //N:1 request_id
    @JoinColumn
    private FoodProfile foodProfile;

    //status
    @Column(nullable = false)
    private Boolean status;
}
