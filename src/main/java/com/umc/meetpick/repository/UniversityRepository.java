package com.umc.meetpick.repository;

import com.umc.meetpick.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    @Query("SELECT u FROM university u WHERE u.universityName LIKE %:keyword%")
    List<University> findByNameContaining(@Param("keyword") String keyword);

}
