package com.umc.meetpick.repository;

import com.umc.meetpick.entity.KakaoMember;
import com.umc.meetpick.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoMemberRepository extends JpaRepository<KakaoMember, Long> {
}
