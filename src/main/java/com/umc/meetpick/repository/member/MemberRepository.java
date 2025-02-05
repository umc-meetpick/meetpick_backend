package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // Optional로 바꾸기
    Member findMemberById(Long id);
    Optional<Member> findBySocialId(Long socialId);
    Optional<Member> findBySocialIdAndSocialType(Long socialId, SocialType socialType);
}
