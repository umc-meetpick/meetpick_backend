package com.umc.meetpick.repository;

import com.umc.meetpick.entity.Hobby;
import com.umc.meetpick.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
}
