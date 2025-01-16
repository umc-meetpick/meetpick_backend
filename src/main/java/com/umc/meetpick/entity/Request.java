package com.umc.meetpick.entity;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.MBTI;
import com.umc.meetpick.enums.NotificationType;
import jakarta.persistence.*;

@Entity
public class Request extends BaseTimeEntity {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키
    @ManyToOne //N:1 major_id
    @JoinColumn(name = "major_id")
    private Major major;

    @ManyToOne //N:1 request_id
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;

    @ManyToOne //N:1 writer_id
    @JoinColumn(name = "writer_id")
    private Member writer;

    //student_number
    @Column(nullable = false)
    private Integer studentNumber;

    //personality
    @Enumerated(EnumType.STRING) //enum
    @Column(nullable = false)
    private MBTI mbti;

    //min_age
    @Column(nullable = false)
    private Integer minAge;

    //max_age
    @Column(nullable = false)
    private Integer maxAge;

    //min_time -> 개선 할 수 있나
    @Column(nullable = false)
    private Integer minTime;

    //max_time
    @Column(nullable = false)
    private Integer maxTime;

    //food
    @Enumerated(EnumType.STRING) //enum
    @Column(nullable = false)
    private FoodType food;

    //max_people
    @Column(nullable = false)
    private Integer maxPeople;

    //current_people
    @Column(nullable = false)
    private Integer currentPeople;

    //type
    @Enumerated(EnumType.STRING) //enum
    @Column(nullable = false)
    private NotificationType type;

    // 인원 수 초과 방지
    public void addPerson() {
        if (currentPeople + 1 > maxPeople) {
            throw new IllegalArgumentException("현재 인원이 최대 인원을 초과할 수 없습니다.");
        }
        currentPeople++;
    }
}
