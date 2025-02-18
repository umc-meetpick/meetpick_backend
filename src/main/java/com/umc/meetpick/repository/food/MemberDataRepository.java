package com.umc.meetpick.repository.food;

import com.umc.meetpick.entity.matchingdata.food.MemberData;
import com.umc.meetpick.enums.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberDataRepository extends JpaRepository<MemberData, Long> {
    List<MemberData> findAllByUniversity(@Param("university") University university);
}
