package com.umc.meetpick.entity;
import jakarta.persistence.*;

@Entity
public class Request {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키

    //student_number
    @Column(nullable = false)
    private String studentNumber;

    //personality
    @Enumerated(EnumType.STRING) //enum
    @Column(nullable = false)
    private Personality personality;

    //min_age
    @Column(nullable = false)
    private Integer minAge;

    //max_age
    @Column(nullable = false)
    private Integer maxAge;

    //min_time
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
    private Type type;


    public enum Personality{
        ISTP,
        ISFP,
        INTP
        //값 나중에

    }

    public enum FoodType{

        KOREAN,       // 한식
        WESTERN,      // 양식
        JAPANESE,     // 일식
        CHINESE,      // 중식
        VIETNAMESE,   // 베트남식
        OTHER         // 기타

    }

    public enum Type{
        type
        //값 나중에

    }
}
