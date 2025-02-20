package com.umc.meetpick.repository.study;


import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.matchingdata.study.MemberDataStudy;
import com.umc.meetpick.enums.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyMemberDataRepository extends JpaRepository<MemberDataStudy, Long> {
    List<MemberDataStudy> findAllByUniversity(@Param("university") University university);
    void deleteByMember(Member member);

    MemberDataStudy findFirstByMember(@Param("member") Member member);
}