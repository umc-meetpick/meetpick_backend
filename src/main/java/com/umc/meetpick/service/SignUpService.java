package com.umc.meetpick.service;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfile;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.repository.MemberProfileRepository;
import com.umc.meetpick.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;

    /**
     * 회원 기본 정보 저장
     * @param memberId 회원 ID
     * @param name 이름
     * @param gender 성별
     * @param birthday 생년월일
     */
    public void saveMemberBasicInfo(Long memberId, String name, String gender, String birthday) {
        log.info("회원 기본 정보 저장 시작: memberId={}, name={}, gender={}, birthday={}",
                memberId, name, gender, birthday);

        // Member 조회 및 기본 정보 업데이트
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        member.setGender(gender.equals("남성") ? Gender.MALE : Gender.FEMALE);
        member.setBirthday(Date.valueOf(birthday));

        memberRepository.save(member);
        log.info("회원 기본 정보 업데이트 완료: {}", member);
    }

    /**
     * 회원 프로필 정보 저장 (학번)
     * @param memberId 회원 ID
     * @param studentNumber 학번
     */
    public void saveMemberProfileInfo(Long memberId, int studentNumber) {
        log.info("회원 프로필 정보 저장 시작: memberId={}, studentNumber={}", memberId, studentNumber);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        // MemberProfile 생성 및 학번 저장
        MemberProfile memberProfile = MemberProfile.builder()
                .member(member)
                .studentNumber(studentNumber)
                .build();

        memberProfileRepository.save(memberProfile);
        log.info("회원 프로필 저장 완료: {}", memberProfile);
    }
}
