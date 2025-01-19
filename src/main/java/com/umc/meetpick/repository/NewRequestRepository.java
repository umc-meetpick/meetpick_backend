package com.umc.meetpick.repository;

import com.umc.meetpick.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 새로운 매칭 요청 api 관련 Repository
@Repository
public interface NewRequestRepository extends JpaRepository<Request, Long> {
}
