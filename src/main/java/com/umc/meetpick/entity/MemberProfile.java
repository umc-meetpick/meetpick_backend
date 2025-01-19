package com.umc.meetpick.entity;
import com.umc.meetpick.enums.ContactType;
import com.umc.meetpick.enums.MBTI;
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
public class MemberProfile extends BaseTimeEntity {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;

    //nickname
    @Column(nullable = false)
    private String nickname;

    //profile_image
    @Column(nullable = false)
    private String profileImage;

    //student_number
    @Column(nullable = false)
    private int studentNumber;

    //MBTI
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MBTI MBTI;

    //contact
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType contact;

    //contactInfo
    @Column(nullable = false)
    private String contactInfo;
}
