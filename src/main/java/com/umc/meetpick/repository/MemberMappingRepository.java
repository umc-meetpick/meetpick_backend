package com.umc.meetpick.repository;

import com.umc.meetpick.entity.mapping.exercise.MemberExerciseMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMappingRepository extends JpaRepository<MemberExerciseMapping, Long> {
}
