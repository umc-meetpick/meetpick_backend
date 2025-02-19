package com.umc.meetpick.repository.food;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.matchingdata.food.MemberDataFood;
import com.umc.meetpick.enums.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberDataRepository extends JpaRepository<MemberDataFood, Long> {
    List<MemberDataFood> findAllByUniversity(@Param("university") University university);
    void deleteByMember(Member member);
}
