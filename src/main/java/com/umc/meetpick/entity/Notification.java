package com.umc.meetpick.entity;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.enums.MateType;
import jakarta.persistence.*;

@Entity
public class Notification extends BaseTimeEntity {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne //1:1 mapping_id 양방향
    @JoinColumn(name = "mapping_id")
    private MemberSecondProfileMapping memberSecondProfileMapping;

    //type
    @Enumerated(EnumType.STRING)  // enum 값을 문자열로 저장
    @Column(nullable = false)
    private MateType type;

    //content
    @Column(nullable = false)
    private String content;

    //is_read
    @Column(nullable = false)
    private Boolean is_read;
}
