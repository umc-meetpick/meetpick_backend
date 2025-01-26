package com.umc.meetpick.repository;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberMapping;
import com.umc.meetpick.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberMappingRepository extends JpaRepository<MemberMapping, Long> {
    Optional<MemberMapping> findByRequest(Request request);
}
