package com.umc.meetpick.repository;

import com.umc.meetpick.entity.mapping.MemberSecondProfileSubMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberSecondProfileSubManjorRepository extends JpaRepository<MemberSecondProfileSubMajor, Long> {
    
}
