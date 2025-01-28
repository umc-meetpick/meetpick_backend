package com.umc.meetpick.repository;

import com.umc.meetpick.entity.mapping.MemberLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberLikesRepository extends JpaRepository<MemberLikes, Long> {
}
