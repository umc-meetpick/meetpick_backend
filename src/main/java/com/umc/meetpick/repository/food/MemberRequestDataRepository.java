package com.umc.meetpick.repository.food;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.matchingdata.food.MemberRequestDataFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRequestDataRepository extends JpaRepository<MemberRequestDataFood,Long> {
    Optional<MemberRequestDataFood> findByMember(@Param("member") Member member);
    void deleteByMember(Member member);
}
