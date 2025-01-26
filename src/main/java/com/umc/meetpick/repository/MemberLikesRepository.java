package com.umc.meetpick.repository;

import com.umc.meetpick.entity.MemberLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberLikesRepository extends JpaRepository<MemberLikes, Long> {
    Optional<MemberLikes> findByRequestId(Long requestId);
}
