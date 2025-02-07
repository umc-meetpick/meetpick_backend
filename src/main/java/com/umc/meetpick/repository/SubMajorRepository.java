package com.umc.meetpick.repository;

import com.umc.meetpick.entity.SubMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubMajorRepository extends JpaRepository<SubMajor, Long> {
    Optional<SubMajor> findByName(String name);

    // TODO 나중에 지울 예정
    SubMajor findByNameOrderByName(String name);
}
