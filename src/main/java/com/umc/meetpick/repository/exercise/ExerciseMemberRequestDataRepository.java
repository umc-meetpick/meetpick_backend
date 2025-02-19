
package com.umc.meetpick.repository.exercise;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.matchingdata.exercise.MemberRequestDataExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExerciseMemberRequestDataRepository extends JpaRepository<MemberRequestDataExercise, Long> {
    Optional<MemberRequestDataExercise> findByMember(@Param("member") Member member);

    Boolean existsByMember(@Param("member") Member member);

    void deleteByMember(Member member);
}