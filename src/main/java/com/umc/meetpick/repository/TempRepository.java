package com.umc.meetpick.repository;

import com.umc.meetpick.entity.Temp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempRepository extends JpaRepository<Temp, Long> {
}
