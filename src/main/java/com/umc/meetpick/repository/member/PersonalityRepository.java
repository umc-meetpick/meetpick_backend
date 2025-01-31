package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.Personality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalityRepository extends JpaRepository<Personality, Long> {
}
