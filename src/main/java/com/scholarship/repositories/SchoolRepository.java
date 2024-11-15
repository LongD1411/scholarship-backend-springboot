package com.scholarship.repositories;

import com.scholarship.entities.Scholarship;
import com.scholarship.entities.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SchoolRepository extends JpaRepository<School, Integer> {
    @Query("SELECT s FROM School s " +
            "WHERE (:keyword IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<School> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    boolean existsByName(String name);
}
