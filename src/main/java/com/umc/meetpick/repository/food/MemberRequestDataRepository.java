package com.umc.meetpick.repository.food;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.matchingdata.food.MemberRequestData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRequestDataRepository extends JpaRepository<MemberRequestData,Long> {
    Optional<MemberRequestData> findByMember(@Param("member") Member member);
}
