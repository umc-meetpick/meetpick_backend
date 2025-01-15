package com.umc.meetpick.entity;
import jakarta.persistence.*;

@Entity
public class MemberProfile {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //외래키

    //nickname
    @Column(nullable = false)
    private String nickname;

    //profile_image
    @Column(nullable = false)
    private String profileImage;

    //student_number
    @Column(nullable = false)
    private String studentNumber;

    //MBTI
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private mbtiType MBTI;

    //contact
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType contact;

    //contactInfo
    @Column(nullable = false)
    private String contactInfo;
    
    public enum ContactType{
        KAKAO_TALK_ID,    // 카카오톡ID
        OPEN_CHAT_LINK,   // 오픈채팅링크
        PHONE_NUMBER      // 전화번호
    }

    public enum mbtiType{
        ISTJ,
        ISFJ,
        INFJ,
        INTJ,

        ISTP,
        ISFP,
        INFP,
        INTP,

        ESTP,
        ESFP,
        ENFP,
        ENTP,

        ESTJ,
        ESFJ,
        ENFJ,
        ENTJ
    }

}
