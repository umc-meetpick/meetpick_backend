package com.umc.meetpick.repository.member;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberMappingRepository extends JpaRepository<MemberSecondProfileMapping, Long> {

    Page<MemberSecondProfileMapping> findAllByMemberSecondProfile_MemberOrderByCreatedAt(@Param("member") Member member, Pageable pageable);

}
