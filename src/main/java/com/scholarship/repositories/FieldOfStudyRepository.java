package com.scholarship.repositories;

import com.scholarship.entities.Country;
import com.scholarship.entities.FieldOfStudy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy,Integer> {

    @Query("SELECT f FROM FieldOfStudy f WHERE (:keyword IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<FieldOfStudy> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    boolean existsByName(String name);
}
