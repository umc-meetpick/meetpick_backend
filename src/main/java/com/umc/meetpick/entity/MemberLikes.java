package com.umc.meetpick.entity;
import jakarta.persistence.*;

@Entity
public class MemberLikes {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키 사용자,공고
    @ManyToOne //N:1 emberprofile_id
    @JoinColumn(name = "memberprofile_id")
    private MemberProfile memberProfile;

    @ManyToOne //N:1 request_id
    @JoinColumn(name = "request_id")
    private Request request;

}
