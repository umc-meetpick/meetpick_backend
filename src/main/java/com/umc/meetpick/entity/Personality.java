//package com.umc.meetpick.entity;
//
//import com.umc.meetpick.enums.PersonalityEnum;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.security.Identity;
//
//@Getter
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//public class Personality {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    private PersonalityEnum groupA;
//
//    @Enumerated(EnumType.STRING)
//    private PersonalityEnum groupB;
//
//    @Enumerated(EnumType.STRING)
//    private PersonalityEnum groupC;
//
//    @Enumerated(EnumType.STRING)
//    private PersonalityEnum groupD;
//
//    public Personality(PersonalityEnum personalityEnum, PersonalityEnum personalityEnum1, PersonalityEnum personalityEnum2, PersonalityEnum personalityEnum3) {
//        this.groupA = personalityEnum;
//        this.groupB = personalityEnum1;
//        this.groupC = personalityEnum2;
//        this.groupD = personalityEnum3;
//    }
//}
//
//// 활기찬 조용한 상관없음
//// 현실적 창의적 상관없음
//// 객관적 공감만땅 상관없음
//// 체계적 유동적 상관없음
