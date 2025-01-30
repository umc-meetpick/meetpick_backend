package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.mapping.MemberSecondProfileLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberLikesRepository extends JpaRepository<MemberSecondProfileLikes, Long> {
    Optional<MemberSecondProfileLikes> findByMemberSecondProfileId(Long requestId);
}
