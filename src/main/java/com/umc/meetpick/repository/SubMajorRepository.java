package com.umc.meetpick.repository;

import com.umc.meetpick.entity.Hobby;
import com.umc.meetpick.entity.SubMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubMajorRepository extends JpaRepository<SubMajor, Long> {
}
