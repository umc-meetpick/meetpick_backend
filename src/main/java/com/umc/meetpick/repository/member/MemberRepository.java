package com.umc.meetpick.repository.member;
import java.util.Optional;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberById(Long id);
    Optional<Member> findBySocialId(Long socialId);
    Optional<Member> findBySocialIdAndSocialType(Long socialId, SocialType socialType);
    Optional<Member> findById(Long id); // ✅ Member 엔티티 조회용
    //TODO 삭제 예정
    Member findFirstBy();
}
