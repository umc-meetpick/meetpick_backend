package com.umc.meetpick.repository.study;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.matchingdata.study.MemberRequestDataStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudyMemberRequestDataRepository extends JpaRepository<MemberRequestDataStudy, Long> {
    Optional<MemberRequestDataStudy> findByMember(@Param("member") Member member);
    void deleteByMember(Member member);
}
