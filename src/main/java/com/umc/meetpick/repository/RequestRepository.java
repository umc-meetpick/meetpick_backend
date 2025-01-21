package com.umc.meetpick.repository;

import com.umc.meetpick.entity.Request;
import com.umc.meetpick.enums.MateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 매칭 관련 Repository
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    boolean existsByWriterIdAndType(Long writerId, MateType type);
}
