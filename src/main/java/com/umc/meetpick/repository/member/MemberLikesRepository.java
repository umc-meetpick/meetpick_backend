package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileLikes;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberLikesRepository extends JpaRepository<MemberSecondProfileLikes, Long> {
    Optional<MemberSecondProfileLikes> findByMemberSecondProfileId(Long requestId);
    List<MemberSecondProfileLikes> findAllByMember(Member member);
    boolean existsByMemberAndMemberSecondProfile(Member member, MemberSecondProfile memberSecondProfile);
}