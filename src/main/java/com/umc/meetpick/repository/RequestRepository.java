package com.umc.meetpick.repository;

import com.umc.meetpick.entity.Request;  // Request 엔티티
import org.springframework.data.domain.Page;  // 페이징
import org.springframework.data.domain.Pageable;  // 페이징
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface RequestRepository extends JpaRepository<Request, Long> {
    // 특정 사용자에게 온 매칭 요청 목록을 페이징하여 조회
    @Query("SELECT r FROM Request r " +
            "JOIN MemberMapping mm ON r = mm.request " +
            "WHERE mm.member.id = :memberId " +
            "ORDER BY r.createdAt DESC")
    Page<Request> findAllRequestsByMemberId(
            @Param("memberId") Long memberId,
            Pageable pageable
    );
}