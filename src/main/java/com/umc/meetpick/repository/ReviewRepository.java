package com.umc.meetpick.repository;

import com.umc.meetpick.entity.Request;
import com.umc.meetpick.entity.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 1. 특정 사용자가 작성한 후기 조회
    List<Review> findByWriter_Id(Long writerId);

    // 2. 특정 사용자를 대상으로 작성된 후기 조회
    List<Review> findByMatchingMember_Id(Long matchingMemberId);
}
