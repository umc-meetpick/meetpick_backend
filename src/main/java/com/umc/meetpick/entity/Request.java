package com.umc.meetpick.entity;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.Hobby;
import com.umc.meetpick.enums.MBTI;
import com.umc.meetpick.enums.MateType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Request extends BaseTimeEntity {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키
//    @ManyToOne //N:1 major_id
//    @JoinColumn(name = "major_id")
//    private Major major;

    // 취미가 같아야 하는지 체크
    @Column(nullable = false)
    private boolean isHobbySame;

    @ManyToOne //N:1 writer_id
    @JoinColumn(name = "writer_id")
    private Member writer;

    //student_number
    @Column(nullable = false)
    private Integer studentNumber;

    //personality
    @Enumerated(EnumType.STRING) // enum 값들을 문자열로 저장
    @ElementCollection(fetch = FetchType.LAZY) // Set을 저장할 때 @ElementCollection 사용
    @CollectionTable(name = "request_mbti", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "mbti") // MBTI 값이 저장될 컬럼 이름
    private Set<MBTI> mbti;

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

    // 음식
    @Enumerated(EnumType.STRING) // enum 값을 문자열로 저장
    @ElementCollection(fetch = FetchType.LAZY) // Set을 저장할 때 @ElementCollection 사용
    @CollectionTable(name = "request_food", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "food") // FoodType 값이 저장될 컬럼 이름
    private Set<FoodType> food;

    //max_people
    @Column(nullable = false)
    private Integer maxPeople;

    //current_people
    @Column(nullable = false)
    private Integer currentPeople;

    //type
    @Enumerated(EnumType.STRING) //enum
    @Column(nullable = false)
    private MateType type;

    // 인원 수 초과 방지
    public void addPerson() {
        if (currentPeople + 1 > maxPeople) {
            throw new IllegalArgumentException("현재 인원이 최대 인원을 초과할 수 없습니다.");
        }
        currentPeople++;
    }
}
