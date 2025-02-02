package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.mapping.MemberSecondProfileSubMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberSecondProfileSubMajorRepository extends JpaRepository<MemberSecondProfileSubMajor, Long> {
}
