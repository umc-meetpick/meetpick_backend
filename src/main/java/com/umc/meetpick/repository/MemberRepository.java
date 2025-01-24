package com.umc.meetpick.repository;

import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberById(Long id);
    Optional<Member> findBySocialId(Long socialId);
}
