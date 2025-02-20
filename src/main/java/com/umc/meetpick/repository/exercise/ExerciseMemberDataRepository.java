
package com.umc.meetpick.repository.exercise;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.matchingdata.exercise.MemberDataExercise;
import com.umc.meetpick.enums.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseMemberDataRepository extends JpaRepository<MemberDataExercise, Long> {
    List<MemberDataExercise> findAllByUniversity(@Param("university") University university);
    Boolean existsByMember(@Param("member") Member member);
    void deleteByMember(Member member);

    MemberDataExercise findFirstByMember(@Param("member") Member member);
}